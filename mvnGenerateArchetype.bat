set atchetypeVersion=%1

mvn archetype:generate -DarchetypeCatalog="local" -DarchetypeGroupId="com.microsoft.azure" -DarchetypeArtifactId="azure-functions-archetype" -DarchetypeVersion="%atchetypeVersion%" -DgroupId="com.microsoft" -DartifactId="e2etestproject" -Dversion="1.0-SNAPSHOT" -Dpackage="com.microsoft" -DappRegion="westus" -DresourceGroup="e2etest-java-functions-group" -DappName="e2etest-java-functions" -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn -B
