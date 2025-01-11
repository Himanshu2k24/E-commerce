<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">E-Commerce</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ml-auto">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="profile.jsp">Profile</a>
                        </li>
                        <li class="nav-item">
                            <form action="UserServlet" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="logout">
                                <button type="submit" class="btn btn-link nav-link" style="display: inline; padding: 0;">Logout</button>
                            </form>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="login.jsp">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="register.jsp">Register</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </nav>
    
    <div class="container">
        <h2 class="mt-5">User Profile</h2>
        <form action="UserServlet" method="post" id="profileForm">
            <input type="hidden" name="action" value="updateProfile">
            
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username" value="${user.username}" readonly>
            </div>
            
            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${user.name}" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                <div class="invalid-feedback">Please enter a valid email.</div>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" value="${user.password}" required>
                <div class="invalid-feedback">Please enter a password.</div>
                <small id="passwordHelp" class="form-text text-muted">Password must be at least 8 characters long.</small>
            </div>
            
            <div class="form-group">
                <label for="address">Address</label>
                <textarea class="form-control" id="address" name="address" rows="3">${user.address}</textarea>
            </div>
            
            <button type="submit" class="btn btn-primary">Update Profile</button>
        </form>
    </div>
    <script src="scripts.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>