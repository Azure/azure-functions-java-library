
set libraryVersion=%1

echo %libraryVersion%
set pluginVersion=%2
echo %pluginVersion%
call mvn versions:set-property -Dproperty=azure.functions.java.library.version -DnewVersion=%libraryVersion%
call mvn versions:set-property -Dproperty=azure.functions.maven.plugin.version -DnewVersion=%pluginVersion%
