--liquibase formatted sql

--changeset kuzin:02_create_link_sequence
CREATE SEQUENCE link_id_seq;


--changeset kuzin:02_create_link_table
CREATE TABLE link
(
    id  BIGINT PRIMARY KEY DEFAULT nextval('link_id_seq'),
    url TEXT NOT NULL
);