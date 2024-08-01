--liquibase formatted sql

--changeset me:1
--comment: initial migration


CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN'),
       ('GUEST'),
       ('ERROR');

COMMENT ON TABLE roles IS 'Таблица с ролями';


CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT NOT NULL,
    login    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role_id  INT REFERENCES roles (id)
);

COMMENT ON TABLE users IS 'Таблица с пользователями';

CREATE TABLE IF NOT EXISTS user_roles
(
    id      SERIAL PRIMARY KEY,
    role_id INt REFERENCES roles (id),
    user_id INT REFERENCES users (id)
);

COMMENT ON TABLE user_roles IS 'Связь пользователей и ролей';