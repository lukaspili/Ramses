# --- !Ups

ALTER TABLE yearcourse DROP CONSTRAINT fkd0084ed8a26de695;
ALTER TABLE yearcourse DROP COLUMN professor_id;

CREATE TABLE yearcourses_professors
(
  yearcourse_id bigint NOT NULL,
  professor_id bigint NOT NULL,
  CONSTRAINT yearcourses_professors_pkey PRIMARY KEY (yearcourse_id , professor_id),
  CONSTRAINT yearcourses_professors_on_yearcourse FOREIGN KEY (yearcourse_id)
      REFERENCES yearcourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT yearcourses_professors_on_user FOREIGN KEY (professor_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);