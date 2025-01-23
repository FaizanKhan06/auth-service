-- Insert roles into role_entity table
INSERT INTO role_entity (role_name) VALUES ('ADMIN');
INSERT INTO role_entity (role_name) VALUES ('USER');
INSERT INTO role_entity (role_name) VALUES ('COMMUNTY HEAD');

-- Insert users into users_details table
INSERT INTO user_entity (email, password_hash, first_name, last_name, phone_no, adhaar_no, is_deleted, role_id)
VALUES ('admin@example.com', '$2a$12$12345passwordHashExample', 'Admin', 'User', '1234567890', '123412341234', FALSE, 1);

INSERT INTO user_entity (email, password_hash, first_name, last_name, phone_no, adhaar_no, is_deleted, role_id)
VALUES ('user@example.com', '$2a$12$67890passwordHashExample', 'John', 'Doe', '9876543210', '432143214321', FALSE, 2);

INSERT INTO user_entity (email, password_hash, first_name, last_name, phone_no, adhaar_no, is_deleted, role_id)
VALUES ('moderator@example.com', '$2a$12$ABCDEpasswordHashExample', 'Jane', 'Smith', '0123456789', '567856785678', FALSE, 3);
