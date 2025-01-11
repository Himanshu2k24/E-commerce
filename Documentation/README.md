# E-Commerce Website
## This Project is under development.
This repository hosts the code for a Java e-commerce Website. This application allows users to browse products, manage their carts, and securely place orders online. The project follows a structured MVC architecture, ensuring scalability and maintainability. Built using Java Servlets, JSP, and MySQL, it provides a seamless shopping experience.
## Features
- Register new account
- Login/Logout
- User Profile
## Technology Stack
### Frontend
- JSP (JavaServer Pages)
- HTML5
- CSS3
- JavaScript
- Bootstrap 4.5.2
### Backend
-  Java Model classes
- Java DAO classes
- Java Servlets
- JDBC
- MySQL 8.0
### Tools & Libraries
- Maven
- jQuery
- Font Awesome
- MySQL Connector/J
### Prerequisites
- JDK 11 or higher
- Apache Tomcat 9.0 or higher
- MySQL 8.0
- Maven 3.x
- IDE (Eclipse/IntelliJ IDEA)
## Database Setup

# 1. Create a new MySQL database:
    - See schema.sql
# 2.Create the required tables:
    - See schema.sql
## Installation & Setup
# 1.Clone the repository:
    git clone https://github.com/Himanshu2k24/E-Commerce_Web.git 
# 2.Configure database connection:
     -  Open src/main/java/com/user/dao/UserDao.java 
     -  Update the database URL, username, and password in other DAO classes
# 3. Build the project:
     mvn clean install
# 4. Deploy to Tomcat:
    -  Copy the WAR file target\user-web-app.war to Tomcat's webapps directory
    -  Start Tomcat server

# 5. Access the application:
    http://localhost:8080/user-web-app 

## Project Structure

   E- Commerce Website

```   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   ├── com/
   │   │   │   │   ├── controller/
   │   │   │   │   ├── Order/
   │   │   │   │   |  |___ model/
   │   │   │   │   |  |___ dao/
   │   │   │   │   |___ Product/
   │   │   │   │   |  |__ model/
   │   │   │   │   |  |__ dao/
   │   │   │   │   ├── User/
   │   │   │   │      |___ model/
   │   │   │   │      |___ dao/
   │   │   │   │   
   │   │   ├─── resources/
   │   │   ├─── webapp/
   │   │   │   ├── WEB-INF/
   │   │   │   │ └── web.xml
   │   │   │   ├── index.jsp
   │   │   │   ├── login.jsp
   │   │   │   ├── profile.jsp
   │   │   │   ├── register.jsp
   │   │   │   ├── styles.css
   │   │   │   └── scripts.js
   │   ├── test/
   │   │ └── java/
   ├── pom.xml
   └── README.md
