# FTMS Backend - Financial Transaction Management System

## Overview
Monolithic Spring Boot application for managing customer accounts, transactions, and compliance.

## Tech Stack
- Java 21
- Spring Boot 3.2
- MySQL 8.0
- Kafka (events)
- Docker

## Documentation
See Confluence: [Link to your Confluence home page]

## Quick Start
\`\`\`bash
./mvnw spring-boot:run
\`\`\`

## Team
- Backend Lead: [Name]
- Tech Lead: [Name]
- DevOps: [Name]

## Run the app
# Start all services
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Clean up (remove volumes)
docker-compose down -v


### Test Mock Server

```bash
# Register customer
curl -X POST http://localhost:8081/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "firstName": "Test",
    "lastName": "User"
  }'


# Get customer
curl http://localhost:8081/api/v1/customers/550e8400-e29b-41d4-a716-446655440000
```