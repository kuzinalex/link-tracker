--liquibase formatted sql


--changeset kuzin:03_subscription_table
CREATE TABLE subscription
(
    chat_id BIGINT REFERENCES chat ON DELETE CASCADE,
    link_id BIGINT REFERENCES link ON DELETE RESTRICT,
    PRIMARY KEY (chat_id, link_id)
);