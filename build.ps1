# A function that checks exit codes and fails script if an error is found 
function StopOnFailedExecution {
  if ($LastExitCode) 
  { 
    exit $LastExitCode 
  }
}

# Clone and install function maven archetype      
git clone https://github.com/Microsoft/azure-maven-archetypes.git -b develop
Push-Location -Path "./azure-maven-archetypes/azure-functions-archetype" -StackName libraryDir
Write-Host "Build and install azure-maven-archetypes" 
cmd.exe /c '.\..\..\mvnBuildSkipTests.bat'
StopOnFailedExecution
Pop-Location -StackName "libraryDir"
$archetypePom = Get-Content ".\azure-maven-archetypes\azure-functions-archetype\pom.xml" -Raw
$archetypePom -match "<version>(.*)</version>"
$atchetypeVersion = $matches[1]
Write-Host "atchetypeVersion: " $atchetypeVersion

# Clone and install function maven plugin
git clone https://github.com/Microsoft/azure-maven-plugins.git -b develop
Push-Location -Path "./azure-maven-plugins" -StackName libraryDir
Write-Host "Build and install azure-functions-maven-plugins" 
cmd.exe /c '.\..\mvnBuildSkipTests.bat'
StopOnFailedExecution
Pop-Location -StackName "libraryDir"
$pluginPom = Get-Content ".\azure-maven-plugins\azure-functions-maven-plugin\pom.xml" | where {$_ -ne ""} 
$nospace = $pluginPom -replace '\s'
$versions =$nospace -match "<version>(.*)<\/version>"
$start = $versions[1].IndexOf('>')+1      
$end = $versions[1].LastIndexOf('<')
$substringLen = $end-$start
$pluginVersion = $versions[1].substring($start, $substringLen)
Write-Host "pluginPomVersion: " $pluginVersion
if ([string]::IsNullOrEmpty($pluginVersion))
{
    exit -1
}
StopOnFailedExecution     


# Get azure-functions-library 
Write-Host "Build and install azure-functions-java-library" 
cmd.exe /c '.\mvnBuild.bat'
$libraryPom = Get-Content "pom.xml" -Raw
$libraryPom -match "<version>(.*)</version>"
$libraryVersion = $matches[1]
Write-Host "libraryVersion: " $libraryVersion

# Generate HttpTrigger Function via archetype version built above
md -Name ciTestDir
Push-Location -Path "./ciTestDir" -StackName libraryDir
Write-Host "Generating project with archetype" 
mvn archetype:generate -DarchetypeCatalog="local" -DarchetypeGroupId="com.microsoft.azure" -DarchetypeArtifactId="azure-functions-archetype" -DarchetypeVersion="$atchetypeVersion" -DgroupId="com.microsoft" -DartifactId="e2etestproject" -Dversion="1.0-SNAPSHOT" -Dpackage="com.microsoft" -DappRegion="westus" -DresourceGroup="e2etest-java-functions-group" -DappName="e2etest-java-functions" -B
StopOnFailedExecution
Pop-Location -StackName "libraryDir"

#Build HttpTrigger Function

Push-Location -Path "./ciTestDir/e2etestproject" -StackName libraryDir
Remove-Item -Recurse -Force "src/test" -ErrorAction Ignore
cmd.exe /c .\..\..\updateVersions.bat $libraryVersion $pluginVersion
StopOnFailedExecution
#Update versions in the HttpTrigger pom.xml
mvn clean package -DskipTests
StopOnFailedExecution
Pop-Location -StackName "libraryDir"



