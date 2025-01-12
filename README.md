# E-Commerce Website
This repository hosts the code for a Java e-commerce Website. This application allows users to browse products, manage their carts, and securely place orders online. The project follows a structured MVC architecture, ensuring scalability and maintainability. Built using Java Servlets, JSP, and MySQL, it provides a seamless shopping experience.
## Features
- Register new account
- Login/Logout
- User Profile
- Cart Option
- Payment option through Credit Card
## 🛠️ Technology Stack
### Frontend
- JSP (JavaServer Pages)
- HTML5
- CSS3
- JavaScript
- Bootstrap 4.5.2
### Backend
- Java Model classes
- Java DAO classes
- Java Servlets
- JDBC
- MySQL 8.0
### Tools & Libraries
- Maven
- jQuery
- Font Awesome
- MySQL Connector/J
### ⚙️ Prerequisites
- JDK 11 or higher
- Apache Tomcat 11.0
- MySQL 8.0
- Maven 3.x
- IDE (Eclipse)
## 🚀 Database Setup

### 1. Create a new MySQL database:
    - See schema.sql
### 2.Create the required tables:
    - See schema.sql
## Installation & Setup
### 1.Clone the repository:
    git clone https://github.com/Himanshu2k24/E-Commerce_Web.git 
### 2.Configure database connection:
     -  Open src/main/java/com/user/dao/UserDao.java 
     -  Update the database URL, username, and password in other DAO classes
### 3. Build the project:
     mvn clean install
### 4. Deploy to Tomcat:
    -  Copy the WAR file target\user-web-app.war to Tomcat's webapps directory
    -  Start Tomcat server

### 5. Access the application:
    http://localhost:8080/user-web-app 

### Project Structure

  📁 E- Commerce Website

```   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   ├── com/
   │   │   │   │   ├── controller/
   |   |   |   |   |── Cart
   |   |   |   |   |    |── dao/
   |   |   |   |   |    |── model/
   │   │   │   │   ├── Order/
   │   │   │   │   |  |___ model/
   │   │   │   │   |  |___ dao/
   │   │   │   │   |___ Product/
   │   │   │   │   |  |__ model/
   │   │   │   │   |  |__ dao/
   │   │   │   │   ├── User/
   │   │   │   │      |___ model/
   │   │   │   │      |___ dao/
   |   |   |   |      |── util/
   │   │   │   │   
   │   │   ├─── webapp/
   |   |   |   |── image/
   │   │   │   ├── WEB-INF/
   │   │   │   │ └── web.xml
   │   │   │   ├── index.jsp
   │   │   │   ├── login.jsp
   │   │   │   ├── profile.jsp
   │   │   │   ├── register.jsp
   │   │   │   ├── styles.css
   │   │   │   └── scripts.js
   |   |   |   |── cart.jsp
   |   |   |   |── payment.jsp
   |   |   |   |── orderconfirmation.jsp
   │   ├── test/
   │   │ └── java/
   ├── pom.xml
   └── README.md
```
![Index Page](https://github.com/user-attachments/assets/3a094e4d-f284-4012-8fe9-e06e5fa9fd30)
![Register Page](https://github.com/user-attachments/assets/1ebec300-2255-4e01-a350-7a850400afac)
![Index Page Scroll](https://github.com/user-attachments/assets/b23e91f8-cd4a-41ca-8ba5-0a657064db01)
![Payment Confirmation Page](https://github.com/user-attachments/assets/f90b4cff-45c8-4d87-af41-b541aa7af180)
![Payment Page](https://github.com/user-attachments/assets/1def4afb-de77-4d23-8fb8-ff00286fed22)
![Cart Page](https://github.com/user-attachments/assets/3a6db967-bad2-4bb8-a001-b7d041dbe3dc)
![Index Page Scroll More](https://github.com/user-attachments/assets/f6f17a4c-0715-4516-8f69-1d144d718e8b)
![Login Page](https://github.com/user-attachments/assets/6132d4b9-cb76-4c72-9138-a4548451960a)
