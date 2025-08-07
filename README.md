# Simple Calendar System

## Quick Start

### Prerequisites

- **Java 21** (OpenJDK 21 or Oracle JDK 21)
- Docker and Docker Compose
- Maven

### 1. Start Infrastructure

```bash
# Start PostgreSQL, Redis, and OTA Mock Service   
docker-compose up -d

# Verify services are running
docker-compose ps
```

### 2. Run the Application

```bash
./mvnw clean compile spring-boot:run
```

### 3. Run test for Application

```bash
./mvnw clean spring-boot:test-run
```

### 3. Run the Frontend part

```bash
cd calendar-fe

npm install

npm start
```

### 4. Service available on http://localhost:3000