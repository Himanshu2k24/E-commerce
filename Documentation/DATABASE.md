# Database Documentation

## Schema

### Users Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Indexes
- PRIMARY KEY on `id`
- UNIQUE INDEX on `username`
- UNIQUE INDEX on `email`

## Common Queries

### User Authentication
```sql
SELECT * FROM users WHERE email = ? AND password = ?;
```

### User Registration
```sql
INSERT INTO users (username, name, email, password, address)
VALUES (?, ?, ?, ?, ?);
```

### Profile Update
```sql
UPDATE users 
SET name = ?, email = ?, password = ?, address = ?
WHERE id = ?;
```

## Backup & Recovery

### Backup Database
```bash
mysqldump -u [username] -p ecommerce > backup.sql
```

### Restore Database
```bash
mysql -u [username] -p ecommerce < backup.sql
```
