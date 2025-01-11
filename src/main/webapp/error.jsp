<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Login Required</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="alert alert-warning" role="alert">
            <h4 class="alert-heading">Login Required!</h4>
            <p>You need to login first before adding items to cart.</p>
            <hr>
            <p class="mb-0">
                <a href="login.jsp" class="btn btn-primary">Login</a>
                <a href="register.jsp" class="btn btn-secondary">Register</a>
                <a href="index.jsp" class="btn btn-link">Back to Home</a>
            </p>
        </div>
    </div>
</body>
</html>
