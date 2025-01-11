package com.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cart.model.Cart;

public class CartDAO {
    private Connection conn;

    public CartDAO(Connection conn) {
        this.conn = conn;
    }

    // Add item to cart
    public boolean addToCart(Cart cart) throws SQLException {
        String sql = "INSERT INTO cart (user_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cart.getUserId());
            stmt.setInt(2, cart.getProductId());
            stmt.setInt(3, cart.getQuantity());
            stmt.setDouble(4, cart.getTotalPrice());
            return stmt.executeUpdate() > 0;
        }
    }

    // Get cart items by user ID
    public List<Cart> getCartItems(int userId) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setTotalPrice(rs.getDouble("total_price"));
                cartItems.add(cart);
            }
        }
        return cartItems;
    }

    // Update cart item quantity
    public boolean updateQuantity(int cartId, int quantity, double totalPrice) throws SQLException {
        String sql = "UPDATE cart SET quantity = ?, total_price = ? WHERE cart_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setDouble(2, totalPrice);
            stmt.setInt(3, cartId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Remove item from cart
    public boolean removeFromCart(int cartId) throws SQLException {
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Clear cart for a user
    public boolean clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
}
