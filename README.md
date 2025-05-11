
# Book Catalog Microservices Lab - Refactoring to Microservices

## Overview
This lab demonstrates the conversion of a monolithic Book Catalog application to a microservices architecture using Spring Cloud components. The system includes:

- **Service Discovery**: Eureka Server for dynamic service registration and discovery
- **Config Service**: Centralized configuration management
- **Book Service**: Core book catalog functionality
- **User Service**: User management and authentication
- **Analytics Service**: Processing and analyzing system events
- **Event-Driven Communication**: Using Apache Kafka for asynchronous messaging

## Architecture Diagram
```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│                 │     │                 │     │                 │
│  Discovery      │     │  Config         │     │  PostgreSQL     │
│  (Eureka Server)│     │  Service        │     │  Databases      │
│                 │     │                 │     │                 │
└────────┬────────┘     └────────┬────────┘     └────────┬────────┘
         │                       │                       │
         │                       │                       │
┌────────▼────────┐     ┌────────▼────────┐     ┌────────▼────────┐
│                 │     │                 │     │                 │
│  User Service   │◄────┤  Book Service   │     │  Kafka          │
│                 │     │                 │     │  (Event Broker) │
└────────┬────────┘     └────────┬────────┘     └────────┬────────┘
         │                       │                       │
         │                       │                       │
┌────────▼────────┐     ┌────────▼────────┐     ┌────────▼────────┐
│                 │     │                 │     │                 │
│  Analytics      │     │  API Gateway    │     │  Client         │
│  Service        │     │  (Future)       │     │  Applications   │
│                 │     │                 │     │                 │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

## Services

### 1. Discovery Service (Eureka Server)
- **Port**: 8761
- **Dashboard**: http://localhost:8761
- **Role**: Service registry and discovery server

### 2. Config Service
- **Port**: 8888
- **Role**: Centralized configuration management using Git backend
- **Config Repository**: `~/config-repo`

### 3. Book Service
- **Port**: 8081
- **Endpoints**:
  - `GET /api/books` - List all books
  - `GET /api/books/{id}` - Get book by ID
  - `GET /api/books/user/{userId}` - Get books by user
  - `POST /api/books` - Create new book
  - `PUT /api/books/{id}` - Update book
  - `DELETE /api/books/{id}` - Delete book
- **Database**: PostgreSQL `bookdb`

### 4. User Service
- **Port**: 8082
- **Endpoints**:
  - `GET /api/users` - List all users
  - `GET /api/users/{id}` - Get user by ID
  - `GET /api/users/username/{username}` - Get user by username
  - `POST /api/users` - Create new user
  - `PUT /api/users/{id}` - Update user
  - `DELETE /api/users/{id}` - Delete user
- **Database**: PostgreSQL `userdb`

### 5. Analytics Service
- **Port**: 8083
- **Endpoints**:
  - `GET /api/analytics/summary` - Get analytics summary
- **Role**: Processes book and user events from Kafka

## Prerequisites
- Java 17+
- Maven
- Docker
- Docker Compose
- IDE (IntelliJ, Eclipse, or VS Code)

## Setup Instructions

1. **Clone the repository** (if applicable)
2. **Initialize the infrastructure**:
   ```bash
   docker-compose up -d
   ```
3. **Initialize the config repository**:
   ```bash
   mkdir -p ~/config-repo
   cd ~/config-repo
   git init
   # Add configuration files as shown in the lab
   git add .
   git commit -m "Initial configuration"
   ```
4. **Build the parent project**:
   ```bash
   mvn clean install
   ```
5. **Start services in order**:
   - Discovery Service
   - Config Service
   - User Service
   - Book Service
   - Analytics Service

## Testing the Services

### Create a User
```bash
curl -X POST http://localhost:8082/api/users   -H "Content-Type: application/json"   -d '{"username":"john_doe","email":"john@example.com","password":"password123"}'
```

### Create a Book
```bash
curl -X POST http://localhost:8081/api/books   -H "Content-Type: application/json"   -d '{"title":"Spring Microservices in Action","author":"John Carnell","isbn":"9781617293986","price":49.99,"userId":1}'
```

### Get Books
```bash
# Get all books
curl http://localhost:8081/api/books

# Get books by user
curl http://localhost:8081/api/books/user/1
```

### Check Analytics
```bash
curl http://localhost:8083/api/analytics/summary
```

### View Service Registry
Open a browser and navigate to: http://localhost:8761

## Key Features Implemented

1. **Service Discovery**: Automatic registration and discovery of services
2. **Centralized Configuration**: Shared configuration managed in Git
3. **Inter-Service Communication**:
   - Synchronous: REST with Feign clients
   - Asynchronous: Event-driven with Kafka
4. **Domain Separation**:
   - Book management
   - User management
   - Analytics processing
5. **Event Sourcing**: Tracking all changes to books and users

## Discussion Questions

1. How does service discovery simplify microservice architecture compared to hard-coded URLs?
2. What are the benefits of centralized configuration in a microservices environment?
3. How does event-driven communication improve system resilience?
4. What challenges might arise when managing transactions across multiple services?
5. How would you handle schema evolution for events in Kafka?

## Troubleshooting

- **Services not registering with Eureka**: Check logs for connectivity issues
- **Configuration not loading**: Verify the config repository is properly initialized
- **Kafka connectivity issues**: Ensure Docker containers are running
- **Database connection problems**: Check PostgreSQL logs and connection strings

## Cleanup
To stop all services and containers:
```bash
# Stop Spring Boot applications (Ctrl+C in each terminal)
# Stop Docker containers
docker-compose down
```

## Next Steps
Potential enhancements for this architecture:
1. Add API Gateway (Spring Cloud Gateway)
2. Implement Circuit Breakers (Resilience4j)
3. Add Distributed Tracing (Sleuth/Zipkin)
4. Implement Security (OAuth2/JWT)
5. Containerize the microservices with Docker.
