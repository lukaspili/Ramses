# --- !Ups

CREATE TABLE specificprestation
(
  id bigint NOT NULL,
  title character varying (255),
  price real,
  hours bigint,
  date date,
  user_id bigint,
  yearcourse_id bigint,
  joborder_id bigint,
  CONSTRAINT specificprestation_pkey PRIMARY KEY (id),
  CONSTRAINT specificprestation_on_user FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT specificprestation_on_yearcourse FOREIGN KEY (yearcourse_id)
		REFERENCES yearcourse (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT specificprestation_on_joborder FOREIGN KEY (joborder_id)
      REFERENCES joborder (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);