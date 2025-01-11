package com.controller;
import com.user.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.product.dao.ProductDAO;
import com.product.model.Product;
import com.cart.dao.CartDAO;
import com.cart.model.Cart;
import com.user.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/CartServlet")  // Changed URL pattern to match the request
public class CartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Update to check for "user" attribute instead of "userId"
        Object user = session.getAttribute("user");
        if (user == null) {
            session.setAttribute("message", "Please login to add items to cart");
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            int userId = ((com.user.model.User) user).getId(); // Get userId from user object
            
            Connection conn = DBConnection.getConnection();
            ProductDAO productDao = new ProductDAO(conn);
            CartDAO cartDao = new CartDAO(conn);
            
            // Get product details
            Product product = productDao.getProductById(productId);
            
            if (product != null) {
                // Create new cart item
                Cart cartItem = new Cart();
                cartItem.setUserId(userId);
                cartItem.setProductId(productId);
                cartItem.setQuantity(1); // Default quantity
                cartItem.setTotalPrice(product.getPrice());
                
                // Save to database
                cartDao.addToCart(cartItem);
                session.setAttribute("message", "Product added to cart successfully!");
            }
            
            response.sendRedirect("cart.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error adding product to cart!");
            response.sendRedirect("cart.jsp");
        }
    }
}
