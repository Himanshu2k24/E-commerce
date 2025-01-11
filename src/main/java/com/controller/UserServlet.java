package com.controller;

import java.io.IOException;

import com.user.dao.UserDAO;
import com.user.model.User;
import com.user.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("register".equals(action)) {
                User user = new User();
                user.setUsername(request.getParameter("username"));
                user.setName(request.getParameter("name"));
                user.setEmail(request.getParameter("email"));
                user.setPassword(request.getParameter("password"));
                user.setAddress(request.getParameter("address"));

                UserDAO userDAO = new UserDAO(DBConnection.getConnection());
                userDAO.addUser(user);
                response.sendRedirect("login.jsp?registered=true");
            } else if ("login".equals(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                
                UserDAO userDAO = new UserDAO(DBConnection.getConnection());
                User user = userDAO.getUserByEmail(email, password);
                
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/products");
                    return;
                } else {
                    response.sendRedirect("login.jsp?error=true");
                }
            } else if ("updateProfile".equals(action)) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    user.setName(request.getParameter("name"));
                    user.setEmail(request.getParameter("email"));
                    user.setPassword(request.getParameter("password"));
                    user.setAddress(request.getParameter("address"));

                    UserDAO userDAO = new UserDAO(DBConnection.getConnection());
                    userDAO.updateUser(user);
                    session.setAttribute("user", user);
                    response.sendRedirect("profile.jsp?updated=true");
                } else {
                    response.sendRedirect("login.jsp");
                }
            } else if ("logout".equals(action)) {
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("viewProfile".equals(action)) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("profile.jsp").forward(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
            } else if ("listUsers".equals(action)) {
                UserDAO userDAO = new UserDAO(DBConnection.getConnection());
                request.setAttribute("users", userDAO.getAllUsers());
                request.getRequestDispatcher("users.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid action");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
