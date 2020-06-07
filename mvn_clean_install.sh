#!/usr/bin/env bash
##
# This script runs maven and try to hadle exceptions when jacoco generate report

##
# for flag 'rerunFailingTestsCount' 
#	see: https://maven.apache.org/surefire/maven-failsafe-plugin/examples/rerun-failing-tests.html

mvn -Dfailsafe.rerunFailingTestsCount=3 clean install
EXIT_CODE=$?

if [[ $EXIT_CODE -ne 0 ]]; then
    echo "=== Running simple retry for exception when generating jacoco report :|"
    mvn -Dfailsafe.rerunFailingTestsCount=3 clean install
fi