package com.order.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.order.model.Order;

import java.util.Date;

public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new order
    public void addOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (user_id, order_date, total_amount) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getUserId());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(3, order.getTotalAmount());
            statement.executeUpdate();
        }
    }

    // Method to retrieve an order by ID
    public Order getOrderById(int id) throws SQLException {
        String query = "SELECT * FROM orders WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDate("order_date"),
                        resultSet.getDouble("total_amount")
                    );
                }
            }
        }
        return null; // Order not found
    }

    // Method to retrieve all orders
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Order order = new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getDate("order_date"),
                    resultSet.getDouble("total_amount")
                );
                orders.add(order);
            }
        }
        return orders;
    }

    // Method to update an order
    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE orders SET user_id=?, order_date=?, total_amount=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getUserId());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(3, order.getTotalAmount());
            statement.setInt(4, order.getId());
            statement.executeUpdate();
        }
    }

    // Method to delete an order by ID
    public void deleteOrder(int id) throws SQLException {
        String query = "DELETE FROM orders WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
