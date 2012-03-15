# --- !Ups

ALTER TABLE users ADD COLUMN passwordresetkey character varying(255);
ALTER TABLE users ADD COLUMN passwordresetdate date;