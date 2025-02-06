-- Insert roles into role_entity table
INSERT INTO role_entity
    (role_name)
VALUES
    ('ADMIN');
INSERT INTO role_entity
    (role_name)
VALUES
    ('USER');
INSERT INTO role_entity
    (role_name)
VALUES
    ('COMMUNTY HEAD');


insert into user_entity (user_id, email, password_hash, first_name, last_name, phone_no, adhaar_no, is_deleted, role_id) values (1, 'a@a.com', 	'$2a$10$BnDp5gXiLOfxYfEi19hZNuloCY/UXwPl4Qzj621PHIlzhJ72xhrI.', 'a', 'a', '234','234',false,3);

insert into user_entity (user_id, email, password_hash, first_name, last_name, phone_no, adhaar_no, is_deleted, role_id) values (2, 'b@b.com', 	'$2a$10$BnDp5gXiLOfxYfEi19hZNuloCY/UXwPl4Qzj621PHIlzhJ72xhrI.', 'b', 'b', '345','345',false,2);

insert into user_entity (user_id, email, password_hash, first_name, last_name, phone_no, adhaar_no, is_deleted, role_id) values (3, 'c@c.com', 	'$2a$10$BnDp5gXiLOfxYfEi19hZNuloCY/UXwPl4Qzj621PHIlzhJ72xhrI.', 'c', 'c', '234','234',false,2);

insert into user_entity (user_id, email, password_hash, first_name, last_name, phone_no, adhaar_no, is_deleted, role_id) values (4, 'd@d.com', 	'$2a$10$BnDp5gXiLOfxYfEi19hZNuloCY/UXwPl4Qzj621PHIlzhJ72xhrI.', 'd', 'd', '234','234',false,2);