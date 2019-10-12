DROP TABLE IF EXISTS users;
DROP DATABASE IF EXISTS todo_app_db;

CREATE DATABASE todo_app_db;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50),
    password varchar(255)
)