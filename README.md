# Votação

- Para acessar as informações sobre o desafio abra o arquivo: DESAFIO.md

## Tecnologias utilizadas no backend

- Java 21
- Spring Boot 4.1.1
- Maven
- JUnit
- Swagger
- Validation

## Tecnologias utilizadas para banco de dados

- PostgreSQL

## Tecnologias utilizadas no frontend

- React Versão: 19.2.8
- Typescript
- Material UI Versão: 9.3.1
- Axios
- Vite
- Zod
- React Hook Form

## Teconlogias utilizadas para infraestrutura

- Docker
- Docker Compose

---

# Como rodar a aplicação

## Backend

Requisitos: Java 21, Maven 3.9.12 e Docker

- `cd backend`
- `docker-compose up -d`
- `mvn clean install`
- `mvn spring-boot:run`
- Para rodar os testes: `mvn test`
- Para acessar a API: [http://localhost:8080/api](http://localhost:8080/api)
- Paraa acessar o Swagger: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

## Frontend

Requisitos: Node.js >= v24.13.0

- `cd frontend`
- `npm install`
- `npm run dev`
- Para acessar o frontend: [http://localhost:5173/](http://localhost:5173/)
