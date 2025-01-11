<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-Commerce Website</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles.css">
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/products">E-Commerce</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/profile.jsp">Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/cart.jsp">Cart</a>
                        </li>
                        <li class="nav-item">
                            <form action="${pageContext.request.contextPath}/UserServlet" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="logout">
                                <button type="submit" class="btn btn-link nav-link">Logout</button>
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
        <h2 class="mt-5">Welcome to E-Commerce Website!</h2>

        <!-- Search Bar Section -->
        <div class="mt-4 search-container">
            <form action="SearchServlet" method="get">
                <div class="form-group mr-2">
                    <input type="text" name="query" class="form-control search-box" placeholder="Search for items..." required>
                </div>
                <button type="submit" class="btn btn-primary search-button">Search</button>
            </form>
        </div>

        <!-- Products Section -->
        <div class="row mt-5">
            <c:choose>
                <c:when test=	"${empty products}">
                    <div class="col-12 text-center">
                        <p>No products available at the moment.</p>
                    </div>
                </c:when>
                <c:when test="${not empty error}">
                    <div class="col-12">
                        <div class="alert alert-danger" role="alert">
                            ${error}
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="product" items="${products}">
                        <div class="col-md-3 mb-4">
                            <div class="card h-100">
                                <img src="${not empty product.imageUrl ? product.imageUrl : 'images/default-product.jpg'}" 
                                     class="card-img-top" 
                                     alt="${product.name}"
                                     onerror="this.src='images/default-product.jpg'">
                                <div class="card-body">
                                    <h5 class="card-title">${product.name}</h5>
                                    <p class="card-text">${product.description}</p>
                                    <div class="price-section">
                                        <span class="current-price">₹<fmt:formatNumber value="${product.price}" pattern="#,##0.00"/></span>
                                        <c:if test="${product.mrp > product.price}">
                                            <span class="original-price">
                                                <del>₹<fmt:formatNumber value="${product.mrp}" pattern="#,##0.00"/></del>
                                            </span>
                                            <span class="discount">
                                                ${Math.round((1 - product.price/product.mrp) * 100)}% OFF
                                            </span>
                                        </c:if>
                                    </div>
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.user}">
                                            <a href="CartServlet?id=${product.id}" class="btn btn-primary">Add to Cart</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="login.jsp" class="btn btn-warning w-100 mt-2">Add to Cart</a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
