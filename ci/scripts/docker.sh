#!/bin/bash
set -e

echo "🐳 Building Docker Image..."

VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)

docker build \
  -t ftms-backend:${VERSION} \
  -t ftms-backend:latest \
  -f docker/Dockerfile \
  .

echo "✅ Docker image built: ftms-backend:${VERSION}"