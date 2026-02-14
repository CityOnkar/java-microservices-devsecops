# Microservices Access Guide

## Overview
The platform consists of 10 microservices running on different ports. All services are accessible via `http://localhost:<PORT>` when running locally via Docker Compose.

---

## Available Services

### 1. **User Service** (Port 8081)
- **Base URL:** `http://localhost:8081`
- **Endpoints:**
  - `GET /users` - Retrieve all users
  - `POST /users` - Create a new user
- **Database:** MySQL (userdb)

```bash
# Get all users
curl http://localhost:8081/users

# Create a user
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe","email": "john@example.com"}'
```

---

### 2. **Order Service** (Port 8082)
- **Base URL:** `http://localhost:8082`
- **Endpoints:**
  - `GET /orders` - Retrieve all orders
  - `POST /orders` - Create a new order
- **Database:** MySQL (orderdb)

```bash
# Get all orders
curl http://localhost:8082/orders

# Create an order
curl -X POST http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-001",
    "userId": 1,
    "amount": 99.99
  }'
```

---

### 3. **Inventory Service** (Port 8083)
- **Base URL:** `http://localhost:8083`
- **Endpoints:**
  - `GET /inventory` - Inventory service status
- **Stateless service**

```bash
curl http://localhost:8083/inventory
```

---

### 4. **Auth Service** (Port 8084)
- **Base URL:** `http://localhost:8084`
- **Endpoints:**
  - `GET /auths` - Auth service status
- **Stateless service**

```bash
curl http://localhost:8084/auths
```

---

### 5. **Notification Service** (Port 8085)
- **Base URL:** `http://localhost:8085`
- **Endpoints:**
  - `GET /notifications` - Notification service status
- **Stateless service**

```bash
curl http://localhost:8085/notifications
```

---

### 6. **Shipping Service** (Port 8086)
- **Base URL:** `http://localhost:8086`
- **Endpoints:**
  - `GET /shippings` - Shipping service status
- **Stateless service**

```bash
curl http://localhost:8086/shippings
```

---

### 7. **Payment Service** (Port 8087)
- **Base URL:** `http://localhost:8087`
- **Endpoints:**
  - `GET /payments` - Payment service status
- **Stateless service**

```bash
curl http://localhost:8087/payments
```

---

### 8. **Analytics Service** (Port 8090)
- **Base URL:** `http://localhost:8090`
- **Endpoints:**
  - `GET /analytics` - Analytics service status
- **Stateless service**

```bash
curl http://localhost:8090/analytics
```

---

### 9. **Gateway Service** (Port 8088)
- **Base URL:** `http://localhost:8088`
- **Endpoints:**
  - `GET /gateways` - Gateway service status
- **Stateless service** (API Gateway)

```bash
curl http://localhost:8088/gateways
```

---

### 10. **Config Service** (Port 8089)
- **Base URL:** `http://localhost:8089`
- **Endpoints:**
  - `GET /configs` - Config service status
- **Stateless service**

```bash
curl http://localhost:8089/configs
```

---

## Health & Actuator Endpoints

All services expose actuator endpoints for monitoring:

### Health Check
```bash
curl http://localhost:<PORT>/actuator/health
```

Example:
```bash
curl http://localhost:8081/actuator/health
```

### Metrics
```bash
curl http://localhost:<PORT>/actuator/metrics
```

### Info
```bash
curl http://localhost:<PORT>/actuator/info
```

---

## Port Summary Table

| Service | Port | Database | Type |
|---------|------|----------|------|
| User Service | 8081 | MySQL (userdb) | Stateful |
| Order Service | 8082 | MySQL (orderdb) | Stateful |
| Inventory Service | 8083 | - | Stateless |
| Auth Service | 8084 | - | Stateless |
| Notification Service | 8085 | - | Stateless |
| Shipping Service | 8086 | - | Stateless |
| Payment Service | 8087 | - | Stateless |
| Analytics Service | 8090 | - | Stateless |
| Gateway Service | 8088 | - | Stateless |
| Config Service | 8089 | - | Stateless |

---

## Running the Stack

### Start all services:
```bash
docker-compose up --build
```

### Stop all services:
```bash
docker-compose down
```

### View logs for a specific service:
```bash
docker-compose logs -f <service-name>
# Example:
docker-compose logs -f user-service
```

### Run a specific service:
```bash
docker-compose up <service-name>
# Example:
docker-compose up user-service
```

---

## Connection Retry Configuration

Services with databases use Hikari CP with the following retry settings:
- **Connection Timeout:** 20 seconds
- **Initialization Fail Timeout:** -1 (infinite retry)
- **Max Pool Size:** 10
- **Min Idle:** 5

This allows services to gracefully retry database connections if the database is not immediately ready during startup.

---

## Common Issues

### "500 Internal Server Error" on `/users` or `/orders`
- Ensure the respective MySQL containers are running and healthy
- Check service logs: `docker-compose logs user-service`
- Verify database credentials match in `application.properties`

### Service not responding (timeout)
- Services may still be initializing (can take 20-30 seconds)
- Check if container is running: `docker-compose ps`
- View logs: `docker-compose logs <service-name>`

### Network errors between services
- Ensure all services are on the same network (default Docker Compose network)
- Use service names (e.g., `mysql-user`, `user-service`) when services communicate

---

## Example Workflows

### Create User and Place an Order

```bash
# 1. Get all users
curl http://localhost:8081/users

# 2. Create a new user
USER_ID=$(curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane@example.com"
  }' | jq '.id')

# 3. Create an order for that user
curl -X POST http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d "{
    \"orderId\": \"ORD-002\",
    \"userId\": $USER_ID,
    \"amount\": 149.99
  }"

# 4. View all orders
curl http://localhost:8082/orders
```

---

## API Documentation

Each service's actual endpoints and request/response schemas are defined in their respective controllers. To see specific endpoint details:

```bash
find services -name "*Controller.java" -exec grep -l "@RequestMapping\|@GetMapping\|@PostMapping" {} \;
```

For detailed endpoint specifications, inspect the controller files:
- User Service: [services/user-service/src/main/java/com/devsecops/user/controller/UserController.java](services/user-service/src/main/java/com/devsecops/user/controller/UserController.java)
- Order Service: [services/order-service/src/main/java/com/devsecops/order/controller/OrderController.java](services/order-service/src/main/java/com/devsecops/order/controller/OrderController.java)
- Other Services: Similar structure in their respective `/controller/` directories

