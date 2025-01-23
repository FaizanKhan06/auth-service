CREATE TABLE role_entity (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE user_entity (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_no VARCHAR(15),
    adhaar_no VARCHAR(12),
    is_deleted BOOLEAN DEFAULT FALSE,
    role_id INT,
    CONSTRAINT fk_role
        FOREIGN KEY (role_id) REFERENCES role_entity(role_id)
        ON DELETE SET NULL
);
