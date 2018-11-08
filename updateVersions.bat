set libraryVersion=%1
set pluginVersion=%2
echo setting azure.functions.java.library.version to %libraryVersion%
echo settong azure.functions.maven.plugin.version to %pluginVersion%
call mvn versions:set-property -Dproperty=azure.functions.java.library.version -DnewVersion=%libraryVersion% -U -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B
call mvn versions:set-property -Dproperty=azure.functions.maven.plugin.version -DnewVersion=%pluginVersion% -U -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B
