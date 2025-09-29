# Project CloudBite: An Event-Driven Microservice Application

This project is a backend system for a modern food ordering application, developed as a major component for the BCSE408L Cloud Computing course. It demonstrates a sophisticated, event-driven architecture using multiple microservices that communicate asynchronously.

The core of the project is a stateful `Order Service` that persists data in a PostgreSQL database. Instead of direct, blocking communication, services are decoupled using a RabbitMQ message broker, making the system resilient, scalable, and reflective of modern cloud-native design patterns.

## Architecture

The system follows an event-driven, microservice-based architecture. A client request triggers a series of synchronous pre-checks followed by a cascading chain of asynchronous events managed by RabbitMQ. The `Order Service` acts as the central orchestrator and source of truth, updating its state in the PostgreSQL database as it receives events from other services.



## Technology Stack

* **Backend:** Java 17, Spring Boot
* **Database:** PostgreSQL
* **Messaging:** RabbitMQ
* **Data Access:** Spring Data JPA, Hibernate
* **Messaging Protocol:** Spring AMQP
* **Build Tool:** Maven
* **Infrastructure:** Docker & Docker Compose

## Getting Started

Follow these instructions to get the backend system up and running on your local machine for development and testing purposes.

### Prerequisites

You must have the following software installed on your machine:
* Java Development Kit (JDK) 17 or higher
* Apache Maven
* Docker and Docker Compose

### Installation & Running

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    cd project-cloudbite
    ```

2.  **Start the Infrastructure:**
    This single command will start the PostgreSQL database and the RabbitMQ message broker in the background.
    ```bash
    docker-compose up -d
    ```
    You can verify that the containers are running with `docker ps`. You can also access the RabbitMQ Management UI at `http://localhost:15672` (user: `guest`, pass: `guest`).

3.  **Run the Microservices:**
    Open **five separate terminal windows**. In each terminal, navigate to the respective service directory and run the Spring Boot application using the Maven wrapper.

    * **Terminal 1 (User Stub):**
        ```bash
        cd user-service-stub
        ./mvnw spring-boot:run
        ```
    * **Terminal 2 (Restaurant Stub):**
        ```bash
        cd restaurant-service-stub
        ./mvnw spring-boot:run
        ```
    * **Terminal 3 (Payment Stub):**
        ```bash
        cd payment-service-stub
        ./mvnw spring-boot:run
        ```
    * **Terminal 4 (Kitchen Stub):**
        ```bash
        cd kitchen-service-stub
        ./mvnw spring-boot:run
        ```
    * **Terminal 5 (Main Order Service):**
        ```bash
        cd order-service
        ./mvnw spring-boot:run
        ```
    Wait for all five applications to start up successfully.

## Usage

Once all services are running, you can test the complete workflow by sending a request to the main `Order Service`.

1.  **Trigger the Workflow:**
    Open a new terminal and use `curl` to send a POST request.
    ```bash
    curl -X POST -H "Content-Type: application/json" http://localhost:5100/api/v1/orders
    ```

2.  **Expected Outcome:**
    You will receive an immediate JSON response confirming that the order has been created with a `PENDING` status.
    ```json
    {
      "id": 1,
      "status": "PENDING",
      "creationDate": "2025-09-25T10:30:00.123456"
    }
    ```

3.  **Verify the Asynchronous Flow:**
    Watch the logs in your five running terminals. You will see a cascade of messages as the event flows through the system:
    * `Order Service` logs the initial save and the `OrderCreatedEvent` being published.
    * `Payment Service` logs receiving the event and publishing a `PaymentSuccessfulEvent`.
    * `Kitchen Service` logs receiving the payment event.
    * `Order Service` logs again, showing it received the `PaymentSuccessfulEvent` and updated the order's status to `PAID` in the database.

## Project Structure

The repository is organized as a multi-project setup.
* `docker-compose.yml`: Defines and manages the shared infrastructure (Postgres, RabbitMQ).
* `/order-service`: The main stateful microservice.
* `/*-service-stub`: Four stateless microservices that act as dependencies. Each service is a self-contained Spring Boot application with a professional, layered folder structure.

## Next Steps

The next phase of this project involves:
* Containerizing the main `Order Service` using Docker.
* Deploying the containerized application to a Kubernetes cluster (Minikube).
* Building a CI/CD pipeline in Node-RED to automate the deployment process.
