# --- !Ups

ALTER TABLE users ADD COLUMN desactivated boolean NOT NULL DEFAULT false;


# --- !Downs

ALTER TABLE users DROP COLUMN desactivated;