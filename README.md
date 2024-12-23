# springboot-mysql-custom-queries

This is a **Spring Boot Project** configured with Redis caching using annotations. It demonstrates how to quickly set up Java applications with caching support.

## Features
- **Spring Security** for authentication and authorization
- **MySQL** for relational database support
- **OpenAPI** for API documentation using Swagger
- **Actuator** for application monitoring

## Prerequisites
Before running the application, ensure that you have the following installed:
- **JDK 17** or higher
- **Gradle** (depending on your build tool)
- **MySQL** server 

## Setup and Installation

### 1. Clone the Repository

```bash
git git@github.com:snehar98/springboot_mysql_custom_queries.git
cd springboot_mysql_custom_queries
```

### 2. Configure MySQL
To use MySQL as your database, uncomment the following configurations in the code:

build.gradle
```bash
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation group: 'mysql', name: 'mysql-connector-java', version: '8.0.28'
```
application.yml
```bash
  datasource:
    url: jdbc:mysql://localhost:3306/employee_details_db #create the database with appropriate name
    username: root
    password:
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate.ddl-auto: none
    #generate-ddl: false #Disable schema management
    #show-sql: true #Log SQL queries
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        #generate_statistics: true #Monitor Hibernate performance
```
Add required classes based on requirement

### 3.API Documentation

**Swagger UI**
http://localhost:8080/swagger-ui/index.html

**Application endpoints**
* Go to Swagger UI (above link)
* Servers --> localhost:8080 - Application Server 
* Definition -> springDocDefault

**Actuator endpoints**
* Go to Swagger UI (above link)
* Servers --> localhost:9000 - Management Server
* Definition --> x-actuator

**API Docs**
http://localhost:8080/v3/api-docs

### 4.Security Configuration
* The application utilizes Spring Security to manage authentication for different endpoints.
* Authentication is based on username and password. The credentials are defined in application.yml and are required to access secured endpoints, particularly those in the admin controller. 
* For adding new endpoints:
  * Whitelist endpoints by modifying the SecurityConfig class, where specific paths can be allowed or restricted. 
  * Admin endpoints should be explicitly whitelisted using requestMatchers with role-based access control (RBAC) to ensure only ADMIN users can access those paths. 
  * For general access, endpoints can be whitelisted using the first requestMatchers clause without role-based restrictions.
```bash
 @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) 
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/*", "/v3/api-docs", "/v3/api-docs/*", "/swagger-resources/*", "/webjars/*", "/actuator/*", "/error", "/favicon.ico", "/users/*", "/users/*/*").permitAll() // Permitting specific URLs
                        .requestMatchers("/admin/*", "/admin/*/*").authenticated() // Restricting access to /admin/* to authenticated users
                        .anyRequest().authenticated() // All other requests require authentication
                );
        return http.build();
    }
```
* The application.yml and SecurityConfig files can be customized to enhance or modify security settings, such as enabling/disabling specific authentication methods or refining access control policies.

### 5.Build and Run the Application
```bash
./gradlew build
./gradlew bootRun
```

### 6.Testing the application
To test the endpoints, use the postman collection added to the root directory

**springboot_mysql_custom_queries.postman_collection.json**

### 7.JaCoCo Test Coverage Report
To generate the JaCoCo test report:
```bash
gradle clean test jacocoTestReport
```

* Access the Report:
After running the command, open build/reports/jacoco/test/html/index.html in a web browser to view the test coverage report.