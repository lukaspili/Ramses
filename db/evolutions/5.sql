# --- !Ups

ALTER TABLE course DROP CONSTRAINT fk78a7cc3b9616f28b;
ALTER TABLE course DROP COLUMN type_id;

ALTER TABLE yearcourse ADD COLUMN type_id bigint;
UPDATE yearcourse SET type_id = 1;
ALTER TABLE yearcourse ALTER COLUMN type_id SET NOT NULL;

ALTER TABLE yearcourse ADD CONSTRAINT yearcourse_on_coursetype FOREIGN KEY (type_id) REFERENCES coursetype(id);

CREATE TABLE yearpromotion
(
    id bigint NOT NULL,
    name character varying(255),
    studentsNumber integer NOT NULL,
    year integer NOT NULL,
    promotion character varying(255),
    CONSTRAINT yearpomotion_pkey PRIMARY KEY (id)
);

CREATE TABLE yearpromotion_yearcourse
(
  yearpromotion_id bigint NOT NULL,
  yearcourses_id bigint NOT NULL,
  CONSTRAINT yearpromotion_yearcourse_pkey PRIMARY KEY (yearpromotion_id , yearcourses_id),
  CONSTRAINT yearpromotion_yearcourse_on_yearcourse FOREIGN KEY (yearcourses_id)
      REFERENCES yearcourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT yearpromotion_yearcourse_on_yearpromotion FOREIGN KEY (yearpromotion_id)
      REFERENCES yearpromotion (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);