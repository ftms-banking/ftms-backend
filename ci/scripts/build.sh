#!/bin/bash
set -e

echo "🔨 Building FTMS Backend..."

# Build
./mvnw clean package -DskipTests

echo "✅ Build completed successfully"