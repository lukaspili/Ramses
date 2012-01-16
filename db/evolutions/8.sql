# --- !Ups

ALTER TABLE yearcourse DROP COLUMN startdate;
ALTER TABLE yearcourse DROP COLUMN enddate;

-- ALTER TABLE yearpromotion_yearcourse RENAME TO yearcourses_yearpromotions;

CREATE TABLE realcourse
(
  id bigint NOT NULL,
  startdate date,
  enddate date,
  yearcourse_id bigint NOT NULL,
  yearpromotion_id bigint NOT NULL,
  CONSTRAINT realcourse_pkey PRIMARY KEY (id),
  CONSTRAINT realcourse_on_yearcourse FOREIGN KEY (yearcourse_id)
      REFERENCES yearcourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT realcourse_on_yearpromotion FOREIGN KEY (yearpromotion_id)
      REFERENCES yearpromotion (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE yearcourses_professors;

CREATE TABLE realcourses_professors
(
  realcourse_id bigint NOT NULL,
  professor_id bigint NOT NULL,
  duration bigint,
  CONSTRAINT realcourses_professors_pkey PRIMARY KEY (realcourse_id , professor_id ),
  CONSTRAINT realcourses_professors_on_user FOREIGN KEY (professor_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT realcourses_professors_on_realcourse FOREIGN KEY (realcourse_id)
      REFERENCES realcourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);