<div align="center">

<img src="https://capsule-render.vercel.app/api?type=venom&height=195&color=0:111827,50:7c3aed,100:c084fc&text=E-commerce&fontColor=ffffff&fontSize=40&fontAlignY=40&desc=Full-Stack%20Shopping%20Application&descAlignY=64&descSize=15" width="100%" alt="E-commerce banner"/>

![Frontend](https://img.shields.io/badge/Frontend-React-61DAFB?style=for-the-badge)
![Backend](https://img.shields.io/badge/Backend-Spring%20%2F%20Maven-6DB33F?style=for-the-badge)
![Project](https://img.shields.io/badge/Project-E-commerce-7c3aed?style=for-the-badge)

[Repository](https://github.com/Chakaliprakash/Ecommerce) · [Issues](https://github.com/Chakaliprakash/Ecommerce/issues)

</div>

---

# E-commerce Application

A full-stack shopping application with a React frontend and a Spring Boot REST API.

## Features

- User registration and login.
- Product listing and product details.
- Product creation and updates, including image uploads.
- Shopping cart and checkout interface.
- JWT-based authentication and secured backend routes.

## Architecture

```text
React + Vite → Spring Boot REST API → Spring Data JPA → MySQL
```

## Technology Stack

- Java 21 and Spring Boot
- Spring Security and JWT
- Spring Data JPA and MySQL
- React, Vite, Axios, and Bootstrap
- GitHub Actions for frontend and backend CI workflows

## Run Locally

### Backend

```bash
cd "Ecomerce Backend"
./mvnw spring-boot:run
```

Configure the MySQL database and required application settings before starting the backend. On Windows, use `mvnw.cmd spring-boot:run`.

### Frontend

```bash
cd "Ecomerce Frontend"
npm install
npm run dev
```

Configure the frontend API base URL to match the backend address.

## Security

Use unique local credentials and a strong JWT signing secret. Do not commit production credentials or real secrets.

## Author

[Prakash Chakali](https://github.com/Chakaliprakash)
