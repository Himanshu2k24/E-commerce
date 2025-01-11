import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import com.user.dao.UserDAO;
import com.user.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoTest {
    private Connection connection;
    private UserDAO userDao;
    private User testUser;

    @BeforeEach
    void setUp() throws SQLException {
        // Create the database schema
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) NOT NULL, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "address TEXT, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            }

            userDao = new UserDAO(connection);
            testUser = new User();
            testUser.setUsername("testuser");
            testUser.setName("Test User");
            testUser.setEmail("test@example.com");
            testUser.setPassword("testpass123");
            testUser.setAddress("Test Address");
        } catch (SQLException e) {
            if (connection != null) {
                connection.close();
            }
            throw e;
        }
    }

    @Test
    void testAddUser() throws SQLException {
        // Test adding a user
        assertTrue(userDao.addUser(testUser), "User should be added successfully");
        
        // Verify the user was added
        User retrievedUser = userDao.getUserByEmail(testUser.getEmail(), testUser.getPassword());
        assertNotNull(retrievedUser, "Retrieved user should not be null");
        assertEquals(testUser.getEmail(), retrievedUser.getEmail());
        assertEquals(testUser.getUsername(), retrievedUser.getUsername());
    }

    @Test
    void testGetAllUsers() throws SQLException {
        // Add a test user first
        userDao.addUser(testUser);
        
        // Get all users and verify
        List<User> users = userDao.getAllUsers();
        assertNotNull(users, "Users list should not be null");
        assertFalse(users.isEmpty(), "Users list should not be empty");
        assertTrue(users.size() > 0, "Should have at least one user");
    }

    @Test
    void testUpdateUser() throws SQLException {
        // Add a test user first
        assertTrue(userDao.addUser(testUser), "User should be added successfully");
        
        // Get the user to update
        User retrievedUser = userDao.getUserByEmail(testUser.getEmail(), testUser.getPassword());
        assertNotNull(retrievedUser, "User should be retrieved before update");
        
        // Update user details
        retrievedUser.setName("Updated Name");
        retrievedUser.setAddress("Updated Address");
        
        // Perform update
        assertTrue(userDao.updateUser(retrievedUser), "Update should be successful");
        
        // Verify update
        User updatedUser = userDao.getUserById(retrievedUser.getId());
        assertNotNull(updatedUser, "Updated user should exist");
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("Updated Address", updatedUser.getAddress());
    }

    @Test
    void testDeleteUser() throws SQLException {
        // Add a test user first
        assertTrue(userDao.addUser(testUser), "User should be added successfully");
        
        // Get the user to delete
        User retrievedUser = userDao.getUserByEmail(testUser.getEmail(), testUser.getPassword());
        assertNotNull(retrievedUser, "User should exist before deletion");
        
        // Delete the user
        assertTrue(userDao.deleteUser(retrievedUser.getId()), "Delete should be successful");
        
        // Verify deletion
        User deletedUser = userDao.getUserById(retrievedUser.getId());
        assertNull(deletedUser, "User should not exist after deletion");
    }

    @AfterEach
    void cleanup() {
        try {
            if (connection != null && !connection.isClosed()) {
                try (Statement stmt = connection.createStatement()) {
                    // Clean up the test data
                    stmt.execute("DROP TABLE IF EXISTS users");
                }
                connection.close();
            }
        } catch (SQLException e) {
            // Log error but don't throw as this is cleanup
            e.printStackTrace();
        }
    }
}
