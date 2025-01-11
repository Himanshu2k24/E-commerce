package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.cart.dao.CartDAO;
import com.user.util.DBConnection;

import java.io.IOException;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int cartId = Integer.parseInt(request.getParameter("id"));
        
        try {
            CartDAO cartDao = new CartDAO(DBConnection.getConnection());
            cartDao.removeFromCart(cartId);
            session.setAttribute("message", "Item removed from cart!");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error removing item from cart!");
        }
        
        response.sendRedirect("cart.jsp");
    }
}
