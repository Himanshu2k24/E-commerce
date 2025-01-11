create database userappdb;

use userappdb;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Drop existing products table if exists
DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    mrp DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    category VARCHAR(50),
    brand VARCHAR(50),
    image_url VARCHAR(255) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create cart table
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    total_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX idx_cart_user ON cart(user_id);
CREATE INDEX idx_cart_product ON cart(product_id);

-- Insert sample products
INSERT INTO products (name, description, price, mrp, stock, category, brand, image_url) VALUES
('Smartphone X', 'Latest smartphone with advanced features', 999.99, 1299.99, 50, 'Electronics', 'TechBrand', 'image/1.png'),
('Laptop Pro', 'High-performance laptop for professionals', 1499.99, 1999.99, 30, 'Electronics', 'CompTech', 'image/2.png'),
('Running Shoes', 'Comfortable athletic shoes for runners', 79.99, 99.99, 100, 'Sports', 'SportsFit', 'image/3.png'),
('Coffee Maker', 'Automatic coffee maker with timer', 49.99, 69.99, 40, 'Appliances', 'HomePlus', 'image/4.png'),
('Backpack', 'Durable waterproof backpack', 39.99, 59.99, 75, 'Accessories', 'TravelGear', 'image/5.png'),
('Smart Watch', 'Fitness tracking smartwatch', 199.99, 249.99, 60, 'Electronics', 'TechBrand', 'image/6.png'),
('Wireless Earbuds', 'Premium wireless earbuds', 129.99, 159.99, 45, 'Electronics', 'AudioTech', 'image/7.png'),
('Gaming Mouse', 'High-precision gaming mouse', 59.99, 79.99, 80, 'Gaming', 'GameTech', 'image/8.png'),
('Desk Chair', 'Ergonomic office chair', 199.99, 299.99, 25, 'Furniture', 'OfficePro', 'image/9.png'),
('Water Bottle', 'Insulated stainless steel bottle', 24.99, 34.99, 120, 'Accessories', 'EcoLife', 'image/10.png'),
('Yoga Mat', 'Non-slip exercise yoga mat', 29.99, 39.99, 90, 'Sports', 'FitLife', 'image/11.png'),
('LED Monitor', '27-inch 4K LED monitor', 299.99, 399.99, 35, 'Electronics', 'ScreenTech', 'image/12.png'),
('Bluetooth Speaker', 'Portable wireless speaker', 79.99, 99.99, 55, 'Electronics', 'AudioTech', 'image/13.png'),
('Kitchen Blender', 'High-speed blending system', 89.99, 119.99, 40, 'Appliances', 'HomePlus', 'image/14.png'),
('Fitness Tracker', 'Advanced activity tracking band', 49.99, 69.99, 70, 'Electronics', 'FitTech', 'image/15.png'),
('Gaming Keyboard', 'Mechanical RGB gaming keyboard', 89.99, 129.99, 60, 'Gaming', 'GameTech', 'image/16.png');

-- Create index for faster searches
CREATE INDEX idx_product_category ON products(category);
CREATE INDEX idx_product_status ON products(status);
CREATE INDEX idx_product_price ON products(price);