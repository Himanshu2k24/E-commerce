import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.product.dao.ProductDAO;
import com.product.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProductDaoTest {
    private Connection connection;
    private ProductDAO productDao;
    private Product testProduct;

    @BeforeEach
    void setUp() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "description TEXT, " +
                    "price DOUBLE NOT NULL, " +
                    "mrp DOUBLE NOT NULL, " +
                    "image_url VARCHAR(255))");
            }

            productDao = new ProductDAO(connection);
            testProduct = new Product();
            testProduct.setName("Test Product");
            testProduct.setDescription("Test Description");
            testProduct.setPrice(99.99);
            testProduct.setMrp(119.99);
            testProduct.setImageUrl("test-image.jpg");

            // Insert test product
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("INSERT INTO products (name, description, price, mrp, image_url) " +
                    "VALUES ('Test Product', 'Test Description', 99.99, 119.99, 'test-image.jpg')");
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.close();
            }
            throw e;
        }
    }

    @Test
    void testGetAllProducts() throws SQLException {
        List<Product> products = productDao.getAllProducts();
        
        assertNotNull(products, "Products list should not be null");
        assertFalse(products.isEmpty(), "Products list should not be empty");
        assertTrue(products.size() > 0, "Should have at least one product");
        
        Product firstProduct = products.get(0);
        assertEquals("Test Product", firstProduct.getName());
        assertEquals(99.99, firstProduct.getPrice());
        assertEquals(119.99, firstProduct.getMrp());
    }

    @Test
    void testGetProductById() throws SQLException {
        // Get the first product (should be our test product)
        Product product = productDao.getProductById(1);
        
        assertNotNull(product, "Product should not be null");
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(99.99, product.getPrice());
        assertEquals(119.99, product.getMrp());
        assertEquals("test-image.jpg", product.getImageUrl());
    }

    @Test
    void testGetNonExistentProduct() throws SQLException {
        Product product = productDao.getProductById(999);
        assertNull(product, "Non-existent product should return null");
    }

    @AfterEach
    void cleanup() {
        try {
            if (connection != null && !connection.isClosed()) {
                try (Statement stmt = connection.createStatement()) {
                    // Clean up the test data
                    stmt.execute("DROP TABLE IF EXISTS products");
                }
                connection.close();
            }
        } catch (SQLException e) {
            // Log error but don't throw as this is cleanup
            e.printStackTrace();
        }
    }
}
