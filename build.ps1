# A function that checks exit codes and fails script if an error is found 
function StopOnFailedExecution {
  if ($LastExitCode) 
  { 
    exit $LastExitCode 
  }
}

# Clone and install function maven archetype      
git clone https://github.com/Microsoft/azure-maven-archetypes.git -b develop
mvn -f ".\azure-maven-archetypes\azure-functions-archetype\pom.xml" clean install -DskipTests -U
StopOnFailedExecution
$archetypePom = Get-Content ".\azure-maven-archetypes\azure-functions-archetype\pom.xml" -Raw
$archetypePom -match "<version>(.*)</version>"
$atchetypeVersion = $matches[1]
Write-Host "atchetypeVersion: " $atchetypeVersion

# Clone and install function maven plugin
git clone https://github.com/Microsoft/azure-maven-plugins.git -b develop
mvn -f ".\azure-maven-plugins\azure-functions-maven-plugin\pom.xml" clean install -DskipTests -U
StopOnFailedExecution
$pluginPom = Get-Content ".\azure-maven-plugins\azure-functions-maven-plugin\pom.xml" -Raw
$pluginPom -match "<version>(.*)</version>"
$pluginVersion = $matches[1]
Write-Host "pluginVersion: " $pluginVersion

# Get azure-functions-library version
$libraryPom = Get-Content "pom.xml" -Raw
$libraryPom -match "<version>(.*)</version>"
$libraryVersion = $matches[1]
Write-Host "libraryVersion: " $libraryVersion

# Generate HttpTrigger Function via archetype version built above
mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=com.microsoft.azure  -DarchetypeArtifactId=azure-functions-archetype -DgroupId=com.library.test -DartifactId=ci -Dversion=1.0-SNAPSHOT -DappName=e2etest -DappRegion=Westus -DresourceGroup=e2etestrg
mvn archetype:generate -DarchetypeCatalog="local" -DarchetypeGroupId="com.microsoft.azure" -DarchetypeArtifactId="azure-functions-archetype" -DarchetypeVersion="1.17-SNAPSHOT" -DgroupId="com.microsoft" -DartifactId="e2etestproject" -Dversion="1.0-SNAPSHOT" -Dpackage="com.microsoft" -DappRegion="westus" -DresourceGroup="e2etest-java-functions-group" -DappName="e2etest-java-functions" -B
StopOnFailedExecution

#Update versions in the HttpTrigger pom.xml
mvn versions:set-property -Dproperty=azure.functions.java.library.version -DnewVersion=$libraryVersion
mvn versions:set-property -Dproperty=azure.functions.maven.plugin.version -DnewVersion=$pluginVersion

#Build HttpTrigger Function
Push-Location -Path "./ci" -StackName libraryDir
Remove-Item -Recurse -Force "src/test" -ErrorAction Ignore
mvn clean package -DskipTests
StopOnFailedExecution
Pop-Location -StackName "libraryDir"



