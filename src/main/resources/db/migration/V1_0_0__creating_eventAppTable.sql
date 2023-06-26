DROP TABLE if exists user_roles;
DROP TABLE if exists user_event;
DROP TABLE if exists events;
DROP TABLE if exists contracts;
DROP TABLE if exists users;
DROP SEQUENCE if exists global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    age              INTEGER,   check (age>0),
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE events
(
    id                   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    header               VARCHAR(50)   UNIQUE       NOT NULL,
    description          TEXT,
    price                NUMERIC(8,2) CHECK (price > 0),
    date_time            TIMESTAMP,
    creator              INTEGER                    NOT NULL,
    created              TIMESTAMP    DEFAULT now() NOT NULL,
    FOREIGN KEY (creator) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE contracts
(
    id                   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id              INTEGER                           NOT NULL,
    status               VARCHAR   NOT NULL,
    details              VARCHAR(50)   UNIQUE        NOT NULL,
    content              TEXT,
    date_of_signing      TIMESTAMP                   NOT NULL,
    end_date             TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE user_event
(
    user_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    status   VARCHAR   NOT NULL,
    CONSTRAINT user_event_idx UNIQUE (user_id, event_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, event_id)
);

DELETE FROM user_roles;
DELETE FROM user_event;
DELETE FROM events;
DELETE FROM contracts;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO users (name, email, password, age)
VALUES ('User', 'user@gmail.com', '{noop}password', 27),
       ('User2', 'user2@gmail.com', '{noop}password', 30),
       ('Company', 'company@gmail.com', '{noop}company', 5);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('PRINCIPAL', 100001),
       ('ADMIN', 100002);

INSERT INTO events (header, description, price, date_time, creator)
VALUES ('newEvent', 'java meet up in Spb', 1200, '2023-06-15 10:00:00', 100002);

INSERT INTO contracts (user_id, status, details, content, date_of_signing, end_date)
VALUES (100002, 'APPLY', '2023/1122', 'содержание', '2023-06-14', '2024-06-13');

INSERT INTO user_event (user_id, event_id, status)
VALUES (100001, 100003, 'APPLY');