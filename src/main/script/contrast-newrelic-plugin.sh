#!/bin/bash

# Ensure JAVA_HOME is set

# Build the Classpath
CP="./contrast-newrelic-plugin.jar"

for f in `ls lib`
do
    CP="$CP:lib/$f"
done

java -cp "$CP" com.aspectsecurity.contrast.integration.newrelic.Main