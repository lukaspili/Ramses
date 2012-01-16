# --- !Ups

ALTER TABLE realcourses_professors ADD COLUMN joborder_id bigint;
ALTER TABLE realcourses_professors ADD CONSTRAINT realcourses_professor_on_joborder FOREIGN KEY (joborder_id)
	REFERENCES joborder(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;