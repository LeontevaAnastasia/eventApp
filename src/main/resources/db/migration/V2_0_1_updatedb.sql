DELETE FROM user_roles;
DELETE FROM user_event;
DELETE FROM events;
DELETE FROM contracts;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO users (name, email, password, age)
VALUES ('User', 'user@gmail.com', '{noop}password', 27),
       ('User2', 'user2@gmail.com', '{noop}password', 30),
       ('Company', 'company@gmail.com', '{noop}company', null);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('PRINCIPAL', 100001),
       ('ADMIN', 100002);

INSERT INTO events (header, description, price, date_time, creator)
VALUES ('newEvent', 'java meet up in Spb', 1200, '2023-06-15 10:00:00', 100002);

INSERT INTO contracts (user_id, status, details, content, date_of_signing, end_date)
VALUES (100002, 'ACCEPTED', '2023/1122', 'содержание', '2023-06-14', '2024-06-13');

INSERT INTO user_event (user_id, event_id, status)
VALUES (100001, 100003, 'APPLY');