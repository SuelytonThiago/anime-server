# anime-server

# About this project

Sharing a personal project of a REST API that was developed inspired by an anime website, using Java in conjunction with Spring Boot. I implemented a robust security layer, employing Spring Security, JSON Web Tokens (JWT) and a token refresh mechanism, which allows the access token to be renewed when necessary. Additionally, we use authorization functions to distinguish between administrators and users by restricting access to specific endpoints, such as delete operations.
One of the notable aspects of the project is the implementation of two-way relationship in some classes. This allows, for example, when deleting a comment, the likes associated with it are automatically removed in cascade, contributing to data consistency.

## Class - diagram
![ANIME-SERVER-DIAGRAM](https://github.com/SuelytonThiago/anime-server/assets/117487495/979e0bb4-e2f0-4e67-8338-f23a3ad02e63)


## Object - diagram

![ANIME-SERVER-OBJECT-DIAGRAM](https://github.com/SuelytonThiago/anime-server/assets/117487495/bbbe7372-90ea-4ba6-8651-5a52fa77c344)


# Technologies used

- Java
- Spring boot
- Maven
- Jpa/Hibernate
- Spring Security
- Json Web Token
- PostgreSQL Database
- Swagger

# How to run the project

### clone repository
git clone `https://github.com/SuelytonThiago/anime-server`

### run the project
./mvnw spring-boot:run

Before starting the project, **configure the environment variables in the application.properties** file.

To test the endpoints, just use postman or access the swagger documentation with the url: localhost:(your port)/swagger-ui/index.html

As the project needs authentication to access an endpoint, use the following user for testing:

### user
* email: `joe@gmail.com`
* password: senha123

### admin
* email: `admin@gmail.com`
* password: senha123

# Author
- Suelyton Thiago de Lima souza

https://www.linkedin.com/in/suelyton-souza-0baaa127a/
