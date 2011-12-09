# --- !Ups

CREATE TABLE build (
    id bigint NOT NULL,
    changelog character varying(255),
    date date
);


CREATE TABLE contract (
    id bigint NOT NULL,
    pdf character varying(255),
    state character varying(255),
    year integer NOT NULL
);


CREATE TABLE course (
    id bigint NOT NULL,
    code character varying(255),
    name character varying(255),
    promotion character varying(255),
    type_id bigint
);



CREATE TABLE coursetype (
    id bigint NOT NULL,
    name character varying(255),
    price real NOT NULL
);


CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE joborder (
    id bigint NOT NULL,
    creationdate date,
    pdf character varying(255),
    state character varying(255),
    total real NOT NULL,
    contract_id bigint,
    user_id bigint
);


CREATE TABLE joborder_soeexam (
    joborder_id bigint NOT NULL,
    soeexams_id bigint NOT NULL
);


CREATE TABLE joborder_yearcourse (
    orders_id bigint NOT NULL,
    courses_id bigint NOT NULL
);



CREATE TABLE soeexam (
    id bigint NOT NULL,
    date bytea,
    plannifiedduration integer NOT NULL,
    realduration integer NOT NULL,
    state character varying(255),
    course_id bigint
);


CREATE TABLE soeexam_users (
    soeexam_id bigint NOT NULL,
    examinators_id bigint NOT NULL
);


CREATE TABLE users (
    id bigint NOT NULL,
    active boolean NOT NULL,
    city character varying(255),
    firstname character varying(255),
    idbooster character varying(255),
    lastname character varying(255),
    password character varying(255),
    postalcode character varying(255),
    profile character varying(255),
    rcs character varying(255),
    siret character varying(255),
    street character varying(255),
    contract_id bigint
);



CREATE TABLE users_course (
    users_id bigint NOT NULL,
    skills_id bigint NOT NULL
);



CREATE TABLE yearcourse (
    id bigint NOT NULL,
    duration integer NOT NULL,
    enddate date,
    startdate date,
    year integer NOT NULL,
    course_id bigint,
    professor_id bigint
);

CREATE TABLE yearcourse_users (
    yearcourse_id bigint NOT NULL,
    candidates_id bigint NOT NULL
);

ALTER TABLE ONLY build
    ADD CONSTRAINT build_pkey PRIMARY KEY (id);



ALTER TABLE ONLY contract
    ADD CONSTRAINT contract_pkey PRIMARY KEY (id);

ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (id);


ALTER TABLE ONLY coursetype
    ADD CONSTRAINT coursetype_pkey PRIMARY KEY (id);


ALTER TABLE ONLY joborder
    ADD CONSTRAINT joborder_pkey PRIMARY KEY (id);


ALTER TABLE ONLY joborder_soeexam
    ADD CONSTRAINT joborder_soeexam_pkey PRIMARY KEY (joborder_id, soeexams_id);


ALTER TABLE ONLY joborder_yearcourse
    ADD CONSTRAINT joborder_yearcourse_pkey PRIMARY KEY (orders_id, courses_id);



ALTER TABLE ONLY soeexam
    ADD CONSTRAINT soeexam_pkey PRIMARY KEY (id);



ALTER TABLE ONLY soeexam_users
    ADD CONSTRAINT soeexam_users_pkey PRIMARY KEY (soeexam_id, examinators_id);



ALTER TABLE ONLY users_course
    ADD CONSTRAINT users_course_pkey PRIMARY KEY (users_id, skills_id);


ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


ALTER TABLE ONLY yearcourse
    ADD CONSTRAINT yearcourse_pkey PRIMARY KEY (id);


ALTER TABLE ONLY yearcourse_users
    ADD CONSTRAINT yearcourse_users_pkey PRIMARY KEY (yearcourse_id, candidates_id);


ALTER TABLE ONLY joborder_yearcourse
    ADD CONSTRAINT fk197ae2622343a10 FOREIGN KEY (courses_id) REFERENCES yearcourse(id);


ALTER TABLE ONLY joborder_yearcourse
    ADD CONSTRAINT fk197ae26e2d5e617 FOREIGN KEY (orders_id) REFERENCES joborder(id);


ALTER TABLE ONLY yearcourse_users
    ADD CONSTRAINT fk1b1abde136da6a14 FOREIGN KEY (candidates_id) REFERENCES users(id);


ALTER TABLE ONLY yearcourse_users
    ADD CONSTRAINT fk1b1abde1dd6e6b30 FOREIGN KEY (yearcourse_id) REFERENCES yearcourse(id);


ALTER TABLE ONLY soeexam_users
    ADD CONSTRAINT fk4bb80831553a1044 FOREIGN KEY (soeexam_id) REFERENCES soeexam(id);


ALTER TABLE ONLY soeexam_users
    ADD CONSTRAINT fk4bb80831a79d01eb FOREIGN KEY (examinators_id) REFERENCES users(id);


ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e08bae9344b FOREIGN KEY (contract_id) REFERENCES contract(id);


ALTER TABLE ONLY course
    ADD CONSTRAINT fk78a7cc3b9616f28b FOREIGN KEY (type_id) REFERENCES coursetype(id);


ALTER TABLE ONLY users_course
    ADD CONSTRAINT fk929ee2525e1b5c5c FOREIGN KEY (users_id) REFERENCES users(id);


ALTER TABLE ONLY users_course
    ADD CONSTRAINT fk929ee2529a7c21a9 FOREIGN KEY (skills_id) REFERENCES course(id);


ALTER TABLE ONLY joborder
    ADD CONSTRAINT fka2b42db1652a83b9 FOREIGN KEY (user_id) REFERENCES users(id);


ALTER TABLE ONLY joborder
    ADD CONSTRAINT fka2b42db1bae9344b FOREIGN KEY (contract_id) REFERENCES contract(id);


ALTER TABLE ONLY joborder_soeexam
    ADD CONSTRAINT fkafd7289a62a70641 FOREIGN KEY (soeexams_id) REFERENCES soeexam(id);


ALTER TABLE ONLY joborder_soeexam
    ADD CONSTRAINT fkafd7289a986d8bcb FOREIGN KEY (joborder_id) REFERENCES joborder(id);

ALTER TABLE ONLY yearcourse
    ADD CONSTRAINT fkd0084ed87b1e0730 FOREIGN KEY (course_id) REFERENCES course(id);


ALTER TABLE ONLY yearcourse
    ADD CONSTRAINT fkd0084ed8a26de695 FOREIGN KEY (professor_id) REFERENCES users(id);


ALTER TABLE ONLY soeexam
    ADD CONSTRAINT fke9bf9528e50dfecd FOREIGN KEY (course_id) REFERENCES yearcourse(id);
