# RESTful web service that implements the functionality for working with the news management system

## Used technologies
* Java 17
* Gradle 7.6
* BD: PostgreSQL
* Cache: Redis
* Spring Boot 3
* Spring Data JPA
* Spring AOP
* Spring Security
* Spring Cloud Config
* TestContainers
* WireMock
* Swagger
* Docker

## News service

Service for performing operations with news and comments. Uses cache depending on the selected profile (prod - Redis, dev - custom LRU or LFU cache  implementation)

**Endpoints**:    
**GET** */news* - Find all news   
**GET** */news/search* - Find news by request params  
**GET** */news/{id}* - Get news by ID   
**GET** */news/{id}/comments* - Get news by ID with comments   
**POST** */news* - Create new news  
**PATCH** */news/{id}* - Update news fields   
**DELETE** */news/{id}* - Delete news by ID

**GET** */comments* - Find all comments   
**GET** */comments/search* - Find comments by request params    
**GET** *comments/{id}* - Get comment by ID   
**POST** */comments* - Create new comment   
**PATCH** */comments/{id}* - Update comment fields    
**DELETE** */comments/{id}* - Delete comment by ID

## Users service

Service for performing operations with users and authentication by JWT token. All requests except get require a JWT token

**Endpoints**:    
**GET** */users* - Find all users   
**GET** */users/{id}* - Get users by ID   
**POST** */users* - Create new user  
**PATCH** */users/{id}* - Update user fields   
**DELETE** */users/{id}* - Delete user by ID

**POST** */auth* - Authentication by JWT token

## Configs service
Service for externalized configuration in system. Uses [github](https://github.com/dtalyanin/ClevertecNewsSystemConfigs) to store configuration

## Exceptions starter
Used for storing exception handlers for all services

## Loggers starter
Used to log requests and responses from controller layers