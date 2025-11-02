#!/bin/bash
set -e

echo "🧪 Running Tests..."

# Unit tests
./mvnw test

# Coverage
./mvnw jacoco:report

echo "✅ All tests passed"