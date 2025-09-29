# Project CloudBite: An Event-Driven Microservice Application

This project is a backend system for a modern food ordering application, developed as a major component for the BCSE408L Cloud Computing course. It demonstrates a sophisticated, event-driven architecture using multiple microservices that communicate asynchronously.

The application is containerized using **Docker** and deployed to a local **Kubernetes (Minikube)** cluster. The deployment process is fully automated via a **CI/CD pipeline built in Node-RED**, showcasing a complete workflow from deployment to verification. The system's architecture is designed to be resilient and scalable, leveraging a **PostgreSQL** database for stateful persistence and a **RabbitMQ** message broker for decoupled, asynchronous communication.

## Architecture

The system follows an event-driven, microservice-based architecture. A client request triggers a series of synchronous pre-checks followed by a cascading chain of asynchronous events managed by RabbitMQ. The containerized `Order Service` is deployed on Kubernetes, while the `docker-compose` stack provides the necessary backing services (database and message broker).



## Technology Stack

* **Backend:** Java 17, Spring Boot
* **Database:** PostgreSQL
* **Messaging:** RabbitMQ
* **Containerization:** Docker
* **Orchestration:** Kubernetes (Minikube)
* **CI/CD Workflow:** Node-RED
* **Data Access:** Spring Data JPA, Hibernate
* **Build Tool:** Maven
* **Infrastructure:** Docker Compose

## Getting Started

Follow these instructions to get the entire system running on your local machine for a full demonstration.

### Prerequisites

You must have the following software installed:
* Java Development Kit (JDK) 17 or higher
* Apache Maven
* Docker and Docker Compose
* Minikube
* `kubectl` (Kubernetes command-line tool)
* Node.js and Node-RED

### Running the Full System (Demo Setup)

1.  **Start Infrastructure:** In the project's root directory, start PostgreSQL and RabbitMQ.
    ```bash
    docker-compose up -d
    ```

2.  **Start Kubernetes Cluster:**
    ```bash
    minikube start
    ```

3.  **Start Network Tunnel:** Open a **new, dedicated terminal window** that you will keep open. This command creates a network bridge to your cluster.
    ```bash
    # Use sudo if your service port is < 1024 (e.g., 80)
    minikube tunnel
    ```

4.  **Build the Docker Image:** Point your terminal to Minikube's Docker environment and build the image for the main service.
    ```bash
    eval $(minikube docker-env)
    cd order-service
    docker build -t order-service:v1 .
    cd .. 
    ```

5.  **Start Stub Services:** Open four separate terminals and start each of the stub applications.
    * **Terminal A:** `cd user-service-stub && ./mvnw spring-boot:run`
    * **Terminal B:** `cd restaurant-service-stub && ./mvnw spring-boot:run`
    * **Terminal C:** `cd payment-service-stub && ./mvnw spring-boot:run`
    * **Terminal D:** `cd kitchen-service-stub && ./mvnw spring-boot:run`

6.  **Start Node-RED:** Open another new terminal to run the pipeline orchestrator.
    ```bash
    node-red
    ```
The entire environment is now set up and ready for your demonstration.

## Demonstrating Key Features

This project is designed to showcase several key administrative tasks and performance objectives.

### 1. CI/CD Pipeline Automation
* **Action:** Navigate to the Node-RED UI at `http://localhost:1880`.
* **Demo:** Click the **`🚀 Deploy Order Service`** button.
* **Verification:** Show the `✅ DEPLOYMENT SUCCESSFUL` message in the debug panel. Then, switch to a terminal and run `kubectl get pods` to show the three `order-service` pods that were just created.

### 2. Load Balancing & Performance Metrics
* **Action:** First, set up three terminals to watch the logs of each pod in real-time (`kubectl logs -f <pod-name>`).
* **Demo:** In a new terminal, use Apache Benchmark (`ab`) to generate traffic:
    ```bash
    # Use the port your minikube tunnel has exposed (e.g., 8080 or 80)
    ab -n 100 -c 10 -m POST [http://127.0.0.1:8080/api/v1/orders](http://127.0.0.1:8080/api/v1/orders)
    ```
* **Verification:**
    * Point to the three log terminals and show how the request logs are appearing on **all of them simultaneously**. This is live load balancing.
    * Show the output from the `ab` command and explain the **Throughput** (`Requests per second`) and **Latency** (`Time per request`) metrics.

### 3. Manual Scaling
* **Action:** Use the `kubectl scale` command to demonstrate administrative control over the application.
* **Demo:**
    1.  Run `kubectl get pods -w` to watch the pods.
    2.  In another terminal, scale up: `kubectl scale deployment order-service-deployment --replicas=5`. Show the new pods being created.
    3.  Scale down: `kubectl scale deployment order-service-deployment --replicas=3`. Show the extra pods being terminated.

## Project Reset / Shutdown
To stop all components of the project, follow these steps:
1.  **Stop Local Services:** `pkill -f 'java'` and `pkill -f 'node-red'`.
2.  **Stop the Tunnel:** Press `Ctrl+C` in the `minikube tunnel` terminal.
3.  **Delete from Kubernetes:** `kubectl delete -f order-service/deployment.yaml -f order-service/service.yaml`.
4.  **Stop Kubernetes Cluster:** `minikube stop`.
5.  **Stop Infrastructure:** `docker-compose down`.
