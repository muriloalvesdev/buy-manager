#!/bin/bash
 export MAVEN_OPTS="-Xmx1G -Xms128m"
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.projectKey=muriloalvesdev_buy-manager
