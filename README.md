# Spring Cloud API Gateway Lab

## Overview
This lab demonstrates the implementation of an API Gateway using Spring Cloud Gateway in a microservices architecture. The setup includes:
- A Eureka service registry
- Two sample microservices (product-service and order-service)
- An API Gateway that routes requests to the appropriate services

## Prerequisites
- Java 17 or higher
- Maven
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Basic knowledge of Spring Boot

## Project Structure
spring-cloud-gateway-lab/
├── api-gateway/             # API Gateway service
├── eureka-server/           # Service discovery server
├── product-service/         # Sample product microservice
└── order-service/           # Sample order microservice

## Getting Started

### 1. Start the Services
Run the services in the following order:

1. **Eureka Server**:
   cd eureka-server
   mvn spring-boot:run

2. **Product Service**:
   cd product-service
   mvn spring-boot:run

3. **Order Service**:
   cd order-service
   mvn spring-boot:run

4. **API Gateway**:
   cd api-gateway
   mvn spring-boot:run

### 2. Verify Services Registration
Check the Eureka dashboard at:  
http://localhost:8761

You should see all three services (api-gateway, product-service, order-service) registered.

## Testing the API Gateway

### Direct Service Access
- Product Service: http://localhost:8081/products
- Order Service: http://localhost:8082/orders

### Through API Gateway
- Product Service via Gateway: http://localhost:8080/api/products
- Order Service via Gateway: http://localhost:8080/api/orders

### Specific Resources
- Get product by ID: http://localhost:8080/api/products/1
- Get order by ID: http://localhost:8080/api/orders/2

## Key Features Implemented

1. **Service Discovery Integration**:
   - Automatic registration with Eureka
   - Dynamic routing using service IDs

2. **Routing Configuration**:
   - Path-based routing to different services
   - Both property-based and programmatic route configuration

3. **Filters**:
   - Global logging filter that adds request headers
   - StripPrefix filter to modify request paths

4. **Advanced Features (Optional)**:
   - Rate limiting
   - Circuit breaking with fallback
   - Load balancing

## Lab Exercises

### Basic Exercise
Add a new microservice (e.g., user-service) and configure the API Gateway to route to it.

### Intermediate Exercise
Implement a custom filter that adds authentication headers to certain routes.

### Advanced Exercise
Implement a rate limiter and circuit breaker for all routes and test their behavior under high load or when services are down.

## Configuration Highlights

### API Gateway Properties
spring.cloud.gateway.discovery.locator.enabled=true  
spring.cloud.gateway.discovery.locator.lower-case-service-id=true  

spring.cloud.gateway.routes[0].id=product-service  
spring.cloud.gateway.routes[0].uri=lb://product-service  
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/products/**  
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1  

### Custom Global Filter
@Component  
public class LoggingFilter implements GlobalFilter {  
    @Override  
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {  
        System.out.println("Path requested: " + exchange.getRequest().getPath());  
        return chain.filter(exchange.mutate()  
            .request(exchange.getRequest()  
                .mutate()  
                .header("X-Request-Time", LocalDateTime.now().toString())  
                .build())  
            .build());  
    }  
}

## Discussion Questions

1. How does API Gateway simplify client interactions in a microservices architecture?  
2. What are the advantages and potential drawbacks of using an API Gateway?  
3. How does Spring Cloud API Gateway compare to other API Gateway solutions?  
4. When would you choose property-based vs programmatic route configuration?  
5. How would you secure your API Gateway in production?

## Troubleshooting

- If services don't register with Eureka:
  - Verify Eureka server is running first
  - Check service configuration for correct Eureka URL
  - Ensure all services have the Eureka client dependency

- If gateway routes don't work:
  - Check service names match exactly
  - Verify the gateway is registered with Eureka
  - Examine gateway logs for routing errors

## Clean Up
Stop all running services with `Ctrl+C` in their respective terminal windows.
