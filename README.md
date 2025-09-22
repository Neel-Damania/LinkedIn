# LinkedIn Clone – Java Spring Boot Microservices

A **LinkedIn clone** built using **Java Spring Boot** with a **microservices architecture**, replicating core professional networking features such as user profiles, connections, posts, messaging, and notifications. The project demonstrates expertise in distributed system design, secure authentication, and scalable deployment.

---

## 🚀 Features

* **Microservices Architecture** – Independent services for authentication, profiles, posts, connections, messaging, and notifications.
* **Secure Authentication** – JWT-based authentication with API Gateway for centralized request handling.
* **User Profiles & Connections** – Create profiles, send/accept connection requests, and manage a professional network.
* **Posts & Feeds** – Share posts, like, and comment with pagination and filtering for performance.
* **Search & Recommendations** – Discover users and content through intelligent search and suggestion features.
* **Notifications** – Real-time notifications for connections, messages, and post interactions.

---

## 🛠️ Tech Stack

* **Backend:** Java, Spring Boot, Spring Data JPA
* **Security:** Spring Security, JWT Authentication, API Gateway
* **Database:** PostgreSQL
* **Communication:** REST APIs, Message Queues
* **Deployment:** Docker

---

## ⚙️ Microservices Overview

1. **Auth Service** – User registration, login, JWT token management.
2. **Profile Service** – Manages user profiles, education, experience.
3. **Post Service** – Handles posts, comments, and likes.
4. **Connection Service** – Send, accept, and manage connection requests.
5. **Messaging Service** – Direct messaging between users.
6. **Notification Service** – Sends alerts for activity and updates.

---

## 📦 Setup & Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/linkedin-clone.git
   ```
2. Configure databases in each service’s `application.properties`.
3. Build and run services:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. Run Docker for Kafka Connection:

   ```bash
   docker-compose up
   ```

---

## 📌 Future Improvements

* Add recommendation engine with machine learning.
* Implement GraphQL for optimized queries.
* Integrate WebSockets for real-time messaging.



---
