# --- !Ups

ALTER TABLE coursetype ADD COLUMN level character varying(255);
ALTER TABLE yearcourse ADD COLUMN name character varying(255);