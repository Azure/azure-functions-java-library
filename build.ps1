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
Write-Host "Deleting Functions Core Tools if exists...."
Remove-Item -Force ./Azure.Functions.Cli.zip -ErrorAction Ignore
Remove-Item -Recurse -Force ./Azure.Functions.Cli -ErrorAction Ignore

Write-Host "Downloading Functions Core Tools...."
Invoke-RestMethod -Uri "https://functionsclibuilds.blob.core.windows.net/builds/$FUNC_RUNTIME_VERSION/latest/version.txt" -OutFile version.txt
Write-Host "Using Functions Core Tools version: $(Get-Content -Raw version.txt)"
Remove-Item version.txt

$url = "https://functionsclibuilds.blob.core.windows.net/builds/$FUNC_RUNTIME_VERSION/latest/Azure.Functions.Cli.$os-$arch.zip"
$output = "$currDir\Azure.Functions.Cli.zip"
$wc = New-Object System.Net.WebClient
$wc.DownloadFile($url, $output)

Write-Host "Extracting Functions Core Tools...."
Expand-Archive ".\Azure.Functions.Cli.zip" -DestinationPath ".\Azure.Functions.Cli"
}
$Env:Path = $Env:Path+";$currDir\Azure.Functions.Cli"

# Clone and build azure-functions-java-worker
git clone https://github.com/azure/azure-functions-java-worker -b shreyasg/v4-extension-bundle
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

