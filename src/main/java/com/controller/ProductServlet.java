package com.controller;

import com.product.dao.ProductDAO;
import com.product.model.Product;
import com.user.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
  
@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/home"})
public class ProductServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Test database connection on servlet initialization
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Servlet initialization - Database connection test successful");
        } catch (Exception e) {
            System.err.println("Servlet initialization - Database connection test failed: " + e.getMessage());
            throw new ServletException("Database connection failed during servlet initialization", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("ProductServlet doGet method called");
        
        // Check if user is logged in
        Object user = request.getSession().getAttribute("user");
        System.out.println("User in session: " + (user != null ? "yes" : "no"));
        
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                throw new ServletException("Database connection failed");
            }
            
            ProductDAO productDAO = new ProductDAO(conn);
            List<Product> products = productDAO.getAllProducts();
            
            System.out.println("Products found: " + (products != null ? products.size() : "null"));
            
            if (products != null && !products.isEmpty()) {
                request.setAttribute("products", products);
            } else {
                request.setAttribute("error", "No products available");
            }
            
            // Always forward to index.jsp
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error in ProductServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error loading products: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}
