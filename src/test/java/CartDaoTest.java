import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import com.cart.dao.CartDAO;
import com.cart.model.Cart;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CartDaoTest {
    private Connection connection;
    private CartDAO cartDao;
    private Cart testCart;

    @BeforeEach
    void setUp() throws SQLException {
        // Create in-memory test database
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        
        // Create necessary tables
        try (Statement stmt = connection.createStatement()) {
            // Create cart table
            stmt.execute("CREATE TABLE IF NOT EXISTS cart (" +
                "cart_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id INT NOT NULL, " +
                "product_id INT NOT NULL, " +
                "quantity INT NOT NULL DEFAULT 1, " +
                "total_price DECIMAL(10,2) NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        }

        cartDao = new CartDAO(connection);
        
        // Create test cart item
        testCart = new Cart();
        testCart.setUserId(1);
        testCart.setProductId(1);
        testCart.setQuantity(2);
        testCart.setTotalPrice(199.98);
    }

    @Test
    void testAddToCart() throws SQLException {
        // Test adding item to cart
        assertTrue(cartDao.addToCart(testCart), "Item should be added to cart successfully");
        
        // Verify item was added
        List<Cart> cartItems = cartDao.getCartItems(testCart.getUserId());
        assertFalse(cartItems.isEmpty(), "Cart should not be empty");
        assertEquals(testCart.getProductId(), cartItems.get(0).getProductId());
        assertEquals(testCart.getQuantity(), cartItems.get(0).getQuantity());
        assertEquals(testCart.getTotalPrice(), cartItems.get(0).getTotalPrice());
    }

    @Test
    void testGetCartItems() throws SQLException {
        // Add multiple items to cart
        cartDao.addToCart(testCart);
        
        Cart secondItem = new Cart();
        secondItem.setUserId(1);
        secondItem.setProductId(2);
        secondItem.setQuantity(1);
        secondItem.setTotalPrice(99.99);
        cartDao.addToCart(secondItem);

        // Test retrieving cart items
        List<Cart> cartItems = cartDao.getCartItems(1);
        assertNotNull(cartItems, "Cart items should not be null");
        assertEquals(2, cartItems.size(), "Should have 2 items in cart");
    }

    @Test
    void testUpdateQuantity() throws SQLException {
        // Add item to cart
        cartDao.addToCart(testCart);
        List<Cart> items = cartDao.getCartItems(testCart.getUserId());
        int cartId = items.get(0).getCartId();

        // Update quantity
        int newQuantity = 3;
        double newTotal = 299.97;
        assertTrue(cartDao.updateQuantity(cartId, newQuantity, newTotal), 
                  "Quantity update should be successful");

        // Verify update
        items = cartDao.getCartItems(testCart.getUserId());
        assertEquals(newQuantity, items.get(0).getQuantity());
        assertEquals(newTotal, items.get(0).getTotalPrice());
    }

    @Test
    void testRemoveFromCart() throws SQLException {
        // Add item to cart
        cartDao.addToCart(testCart);
        List<Cart> items = cartDao.getCartItems(testCart.getUserId());
        int cartId = items.get(0).getCartId();

        // Remove item
        assertTrue(cartDao.removeFromCart(cartId), "Item removal should be successful");

        // Verify removal
        items = cartDao.getCartItems(testCart.getUserId());
        assertTrue(items.isEmpty(), "Cart should be empty after removal");
    }

    @Test
    void testClearCart() throws SQLException {
        // Add multiple items
        cartDao.addToCart(testCart);
        Cart secondItem = new Cart();
        secondItem.setUserId(1);
        secondItem.setProductId(2);
        secondItem.setQuantity(1);
        secondItem.setTotalPrice(99.99);
        cartDao.addToCart(secondItem);

        // Clear cart
        assertTrue(cartDao.clearCart(1), "Cart clearing should be successful");

        // Verify cart is empty
        List<Cart> items = cartDao.getCartItems(1);
        assertTrue(items.isEmpty(), "Cart should be empty after clearing");
    }

    @AfterEach
    void cleanup() {
        try {
            if (connection != null && !connection.isClosed()) {
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("DROP TABLE IF EXISTS cart");
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
