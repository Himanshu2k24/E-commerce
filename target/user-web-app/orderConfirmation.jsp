<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cart.dao.CartDAO" %>
<%@ page import="com.cart.model.Cart" %>
<%@ page import="com.product.dao.ProductDAO" %>
<%@ page import="com.product.model.Product" %>
<%@ page import="com.user.util.DBConnection" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html> 
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .confirmation-box {
            text-align: center;
            padding: 40px;
            border-radius: 10px;
            background-color: #f8f9fa;
            margin-top: 50px;
        }
        .confirmation-icon {
            color: #28a745;
            font-size: 48px;
            margin-bottom: 20px;
        }
        .order-details {
            margin-top: 30px;
            text-align: left;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="confirmation-box">
            <div class="confirmation-icon">✓</div>
            <h2>Order Confirmed!</h2>
            <p class="lead">Thank you for your purchase</p>
            
            <div class="order-details card">
                <div class="card-body">
                    <h5 class="card-title">Order Summary</h5>
                    <%
                        // Get cart total from database
                        Object user = session.getAttribute("user");
                        if (user == null) {
                            response.sendRedirect("login.jsp");
                            return;
                        }
                        
                        int userId = ((com.user.model.User) user).getId();
                        CartDAO cartDao = new CartDAO(DBConnection.getConnection());
                        ProductDAO productDao = new ProductDAO(DBConnection.getConnection());
                        List<Cart> cartItems = cartDao.getCartItems(userId);
                        double totalSum = 0;
                        
                        request.setAttribute("cartItems", cartItems);
                        for(Cart item : cartItems) {
                            Product product = productDao.getProductById(item.getProductId());
                            totalSum += item.getTotalPrice();
                            pageContext.setAttribute("product", product);
                    %>
                        <tr>
                            <td><%= product.getName() %></td>
                            <td>₹<%= item.getTotalPrice() %></td>
                        </tr>
                    <%
                        }
                        pageContext.setAttribute("totalSum", totalSum);
                        
                        // Clear the cart after displaying order
                        cartDao.clearCart(userId);
                    %>
                    <table class="table">
                        <tbody>
                            <tr>
                                <td><strong>Total Amount Paid</strong></td>
                                <td><strong>₹${totalSum + 50.00}</strong></td> <!-- Added shipping cost -->
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="mt-4">
                <a href="products" class="btn btn-primary">Continue Shopping</a>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
