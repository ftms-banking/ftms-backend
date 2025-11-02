#!/bin/bash
set -e

echo "📊 Running SonarQube Analysis..."

./mvnw sonar:sonar \
  -Dsonar.projectKey=ftms-backend \
  -Dsonar.host.url=${SONAR_HOST_URL:-http://localhost:9000} \
  -Dsonar.login=${SONAR_TOKEN:-admin}

echo "✅ SonarQube analysis completed"