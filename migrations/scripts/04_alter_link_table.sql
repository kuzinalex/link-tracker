--liquibase formatted sql


--changeset kuzin:04_alter_link_table
ALTER TABLE link
ADD COLUMN IF NOT EXISTS check_time timestamp DEFAULT NOW(),
ADD COLUMN IF NOT EXISTS updated_at timestamp DEFAULT NOW();