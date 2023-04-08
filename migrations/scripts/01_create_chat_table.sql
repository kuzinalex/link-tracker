--liquibase formatted sql

--changeset kuzin:01_create_chat_table
CREATE TABLE chat
(
    id BIGINT PRIMARY KEY
);