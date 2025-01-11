package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get payment form data
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");
        
        // Basic validation
        if (cardName == null || cardNumber == null || expiryDate == null || cvv == null ||
            cardName.trim().isEmpty() || cardNumber.trim().isEmpty() || 
            expiryDate.trim().isEmpty() || cvv.trim().isEmpty()) {
            
            response.sendRedirect("Payment.jsp?error=invalid");
            return;
        }
        
        try {
            // Process payment (this is where you would integrate with a payment gateway)
            boolean paymentSuccess = processPayment(cardNumber, expiryDate, cvv);
            
            if (paymentSuccess) {
                // Clear the cart after successful payment
                HttpSession session = request.getSession();
                session.removeAttribute("cart");
                
                // Redirect to confirmation page
                response.sendRedirect("orderConfirmation.jsp");
            } else {
                response.sendRedirect("Payment.jsp?error=failed");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Payment.jsp?error=system");
        }
    }
    
    private boolean processPayment(String cardNumber, String expiryDate, String cvv) {
        // This is a dummy implementation
        // In a real application, you would integrate with a payment gateway
        // and perform actual payment processing
        
        // For demonstration, we'll just validate the card number length
        if (cardNumber.length() == 16) {
            return true;
        }
        return false;
    }
}
