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
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Shopping Cart</h2>
        
        <%
            // Check if user is logged in using the "user" attribute
            Object user = session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            int userId = ((com.user.model.User) user).getId(); // Get userId from user object
            CartDAO cartDao = new CartDAO(DBConnection.getConnection());
            ProductDAO productDao = new ProductDAO(DBConnection.getConnection());
            List<Cart> cartItems = cartDao.getCartItems(userId);
            double total = 0;
            
            request.setAttribute("cartItems", cartItems);
        %>
        
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success">
                ${sessionScope.message}
                <% session.removeAttribute("message"); %>
            </div>
        </c:if>

        <c:if test="${empty cartItems}">
            <div class="alert alert-info">Your cart is empty</div>
            <a href="index.jsp" class="btn btn-primary">Continue Shopping</a>
        </c:if>
        
        <c:if test="${not empty cartItems}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <%
                            Cart cartItem = (Cart)pageContext.getAttribute("item");
                            Product product = productDao.getProductById(cartItem.getProductId());
                            total += cartItem.getTotalPrice();
                            pageContext.setAttribute("product", product);
                            pageContext.setAttribute("runningTotal", total); // Add this line
                        %>
                        <tr>
                            <td><img src="${product.imageUrl}" alt="${product.name}" style="max-width: 100px;"></td>
                            <td>${product.name}</td>
                            <td>₹${product.price}</td>
                            <td>${item.quantity}</td>
                            <td>₹${item.totalPrice}</td>
                            <td>
                                <a href="remove-from-cart?id=${item.cartId}" class="btn btn-danger btn-sm">Remove</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4"><strong>Total Amount:</strong></td>
                        <td colspan="2"><strong>₹${runningTotal}</strong></td> <!-- Change this line -->
                    </tr>
                </tfoot>
            </table>
            
            <div class="mt-3">
                <a href="products" class="btn btn-secondary">Continue Shopping</a>
                <a href="Payment.jsp" class="btn btn-success">Proceed to Checkout</a>
            </div>
        </c:if>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
