--liquibase formatted sql

--changeset me:1
--comment: initial migration


--Таблица для ролей

CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

COMMENT ON TABLE roles IS 'Таблица с ролями';

--Таблица для привилегий

CREATE TABLE IF NOT EXISTS privileges
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

INSERT INTO privileges (name)
VALUES ('PRIVILEGE_OPEN_COMMON'),
       ('PRIVILEGE_OPEN_PERSONAL'),
       ('PRIVILEGE_EDIT_COMMON'),
       ('PRIVILEGE_EDIT_PERSONAL');

COMMENT ON TABLE privileges IS 'Таблица с привилегиями для ролей';

--Таблица связи привилегий и ролей

CREATE TABLE IF NOT EXISTS role_privileges
(
    id           SERIAL PRIMARY KEY,
    privilege_id INt REFERENCES privileges (id),
    role_id      INT REFERENCES roles (id)
);

COMMENT ON TABLE role_privileges IS 'Таблица связи привилегий и ролей';

--Таблица для пользователей

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL
);

COMMENT ON TABLE users IS 'Таблица с пользователями';

--Таблица связи пользователей и ролей

CREATE TABLE IF NOT EXISTS user_roles
(
    id      SERIAL PRIMARY KEY,
    role_id INt REFERENCES roles (id),
    user_id INT REFERENCES users (id)
);

COMMENT ON TABLE user_roles IS 'Связь пользователей и ролей';