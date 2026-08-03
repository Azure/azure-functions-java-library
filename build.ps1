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
cmd.exe /c '.\..\mvnBuildFunctionPluginsSkipTests.bat'
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
StopOnFailedExecution
$libraryPom = Get-Content "pom.xml" -Raw
$libraryPom -match "<version>(.*)</version>"
$libraryVersion = $matches[1]
Write-Host "libraryVersion: " $libraryVersion

# Download azure-functions-core-tools
$FUNC_RUNTIME_VERSION = '4'
$arch = [System.Runtime.InteropServices.RuntimeInformation]::OSArchitecture.ToString().ToLowerInvariant()
$os = if ($IsWindows) { "win" } else { if ($IsMacOS) { "osx" } else { "linux" } }

$currDir =  Get-Location
$skipCliDownload = $false
if($args[0])
{
$skipCliDownload = $args[0]
}
Write-Host "skipCliDownload" $skipCliDownload
if(!$skipCliDownload)
{
$FUNC_RUNTIME_VERSION = 'latest'

Write-Host "Installing Core Tools globlally using npm, version: $FUNC_RUNTIME_VERSION ..."

$FUNC_CLI_DIRECTORY = Join-Path $currDir 'Azure.Functions.Cli'
$repoNpmConfig = Join-Path $PSScriptRoot '.npmrc'
if ([string]::IsNullOrWhiteSpace($Env:NPM_CONFIG_USERCONFIG)) {
  $Env:NPM_CONFIG_USERCONFIG = $repoNpmConfig
}
if (-not (Test-Path -LiteralPath $Env:NPM_CONFIG_USERCONFIG -PathType Leaf)) {
  throw "NPM_CONFIG_USERCONFIG does not point to a file: $Env:NPM_CONFIG_USERCONFIG"
}

# 1. Clean previous install
Remove-Item -Recurse -Force $FUNC_CLI_DIRECTORY -ErrorAction Ignore
New-Item -ItemType Directory -Path $FUNC_CLI_DIRECTORY -ErrorAction Ignore

# 2. Locate the global prefix and module root that npm just used
$globalNode   = (npm root   -g | Out-String).Trim()         # e.g. /usr/local/lib/node_modules
$moduleRoot   = Join-Path $globalNode 'azure-functions-core-tools'

# 3. npm install → temp folder
$npmInstallArguments = @(
  'install'
  '--global'
  "azure-functions-core-tools@$FUNC_RUNTIME_VERSION"
  '--unsafe-perm'
  'true'
  '--foreground-scripts'
  '--loglevel'
  'verbose'
)
& npm @npmInstallArguments
StopOnFailedExecution

# 4. Copy CLI payload into the layout required tests
Copy-Item "$moduleRoot\bin\*" $FUNC_CLI_DIRECTORY -Recurse -Force
}
$Env:Path = $Env:Path+";$currDir\Azure.Functions.Cli"
func --version

# Clone and build azure-functions-java-worker
git clone https://github.com/azure/azure-functions-java-worker -b dev
Push-Location -Path "./azure-functions-java-worker" -StackName libraryDir
Write-Host "Updating azure-functions-java-worker to use current version of the java library"

cmd.exe /c .\..\updateVersions.bat $libraryVersion
Write-Host "Building azure-functions-java-worker"
cmd.exe /c '.\mvnBuild.bat'
StopOnFailedExecution
Pop-Location -StackName "libraryDir"

# Update core tools with the new Java worker
Write-Host "Replacing Java worker binaries in the Core Tools..."
Get-ChildItem -Path "./azure-functions-java-worker/target/*" -Include 'azure*' -Exclude '*shaded.jar','*tests.jar' | ForEach-Object {
  Copy-Item $_.FullName "./Azure.Functions.Cli/workers/java/azure-functions-java-worker.jar" -Force -Verbose
}
Copy-Item -Path "./azure-functions-java-worker/annotationLib" -Destination "./Azure.Functions.Cli/workers/java/annotationLib" -Recurse -Verbose
Copy-Item -Path ".\Azure.Functions.Cli" -Destination ".\azure-functions-java-worker\Azure.Functions.Cli" -Recurse

# Updating end to end tests with the new library
Push-Location -Path "./azure-functions-java-worker/endtoendtests" -StackName libraryDir
Write-Host "Updating azure-functions-java-worker endtoendtests to use current version of the java library"

cmd.exe /c .\..\..\updateVersions.bat $libraryVersion $pluginVersion
Write-Host "Building azure-functions-java-worker end to end tests"
cmd.exe /c '.\..\..\mvnBuild.bat'
StopOnFailedExecution
Pop-Location -StackName "libraryDir"

$ApplicationInsightsAgentVersion = '3.5.2'
$ApplicationInsightsAgentFilename = "applicationinsights-agent-${ApplicationInsightsAgentVersion}.jar"
$ApplicationInsightsAgentUrl = "https://repo1.maven.org/maven2/com/microsoft/azure/applicationinsights-agent/${ApplicationInsightsAgentVersion}/${ApplicationInsightsAgentFilename}"

# Download application insights agent from maven central
$ApplicationInsightsAgentFile = "$currDir/$ApplicationInsightsAgentFilename"

# local testing cleanup
if (Test-Path -Path $ApplicationInsightsAgentFile) {
    Remove-Item -Path $ApplicationInsightsAgentFile
}

# local testing cleanup
$oldOutput = [System.IO.Path]::Combine($currDir, "agent")
if (Test-Path -Path $oldOutput) {
    Remove-Item -Path $oldOutput -Recurse
}

# local testing cleanup
$oldExtract = [System.IO.Path]::Combine($currDir, "extract")
if (Test-Path -Path $oldExtract) {
    Remove-Item -Path $oldExtract -Recurse
}

echo "Start downloading '$ApplicationInsightsAgentUrl' to '$currDir'"
try {
    Invoke-WebRequest -Uri $ApplicationInsightsAgentUrl -OutFile $ApplicationInsightsAgentFile
} catch {
    echo "An error occurred. Download fails" $ApplicationInsightsAgentFile
    echo "Exiting"
    exit 1
}

if (-not(Test-Path -Path $ApplicationInsightsAgentFile)) {
    echo "$ApplicationInsightsAgentFile do not exist."
    exit 1
}

$extract = new-item -type directory -force $currDir\extract
if (-not(Test-Path -Path $extract)) {
    echo "Fail to create a new directory $extract"
    exit 1
}

echo "Start extracting content from $ApplicationInsightsAgentFilename to extract folder"
cd -Path $extract -PassThru
Start-Process -FilePath "cmd" -ArgumentList "/c jar xf $ApplicationInsightsAgentFile" -Wait 
cd $currDir
echo "Done extracting"

echo "Unsign $ApplicationInsightsAgentFilename"
Remove-Item $extract\META-INF\MSFTSIG.*
$manifest = "$extract\META-INF\MANIFEST.MF"
$newContent = (Get-Content -Raw $manifest | Select-String -Pattern '(?sm)^(.*?\r?\n)\r?\n').Matches[0].Groups[1].Value
Set-Content -Path $manifest $newContent

Remove-Item $ApplicationInsightsAgentFile
if (-not(Test-Path -Path $ApplicationInsightsAgentFile)) {
    echo "Delete the original $ApplicationInsightsAgentFilename successfully"
} else {
    echo "Fail to delete original source $ApplicationInsightsAgentFilename"
    exit 1
}

$agent = new-item -type directory -force $currDir/agent
$filename = "applicationinsights-agent.jar"
$result = [System.IO.Path]::Combine($agent, $filename)
echo "re-jar $filename"

cd -Path $extract -PassThru
jar cfm $result META-INF/MANIFEST.MF .

if (-not(Test-Path -Path $result)) {
    echo "Fail to re-archive $filename"
    exit 1
}

Write-Host "Creating the functions.codeless file"
New-Item -path $currDir\agent -type file -name "functions.codeless"

Write-Host "Copying the unsigned Application Insights Agent to worker directory"
Copy-Item "$currDir/agent" "$currDir/azure-functions-java-worker/Azure.Functions.Cli/workers/java" -Recurse -Verbose -Force