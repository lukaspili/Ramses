# --- !Ups

DROP TABLE yearcourse_users;

CREATE TABLE course_users
(
  course_id bigint NOT NULL,
  candidates_id bigint NOT NULL,
  CONSTRAINT course_users_pkey PRIMARY KEY (course_id , candidates_id ),
  CONSTRAINT course_users_on_user FOREIGN KEY (candidates_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT course_users_on_course FOREIGN KEY (course_id)
      REFERENCES course (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);