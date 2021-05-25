CREATE SEQUENCE id_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE users (
    id bigint DEFAULT nextval('id_seq'::regclass) NOT NULL,
    username varchar(100) NOT NULL,
    password varchar(50) NOT NULL,
    first_name varchar(60) NOT NULL,
    last_name varchar(60) NOT NULL,
    user_type smallint NOT NULL,
    building_nr integer NOT NULL,
    appartment_nr integer NOT NULL,
    details varchar(255),
    version bigint DEFAULT 0 NOT NULL
);

CREATE TABLE requests (
    id bigint DEFAULT nextval('id_seq'::regclass) NOT NULL,
    name varchar(255) NOT NULL,
    is_resolved boolean NOT NULL,
    details varchar(255),
    due_date timestamp without time zone NOT NULL,
    requestType smallint NOT NULL,
    version bigint DEFAULT 0 NOT NULL
);

CREATE TABLE households (
    id bigint DEFAULT nextval('id_seq'::regclass) NOT NULL,
    building_nr integer NOT NULL,
    appartment_nr integer NOT NULL,
 	rooms_nr integer NOT NULL,
  	nr_curr_occupants integer NOT NULL,
  	total_capacity integer NOT NULL,
    details varchar(255),
    version bigint DEFAULT 0 NOT NULL
);

ALTER TABLE ONLY households
    ADD CONSTRAINT user_household_fkey FOREIGN KEY (household_id) REFERENCES households(id);	

ALTER TABLE ONLY requests
    ADD CONSTRAINT user_requests_fkey FOREIGN KEY (request_id) REFERENCES requests(id);

CREATE TABLE expenses (
    id bigint DEFAULT nextval('id_seq'::regclass) NOT NULL,
    expense_type smallint NOT NULL,
    details varchar(255),
    total_sum double precision NOT NULL,
    leftover_sum double precision NOT NULL,
    payed_in_full boolean NOT NULL,
    due_date timestamp without time zone NOT NULL,
    version bigint DEFAULT 0 NOT NULL
);

--
-- Many to many relationship
--

CREATE TABLE expenses_households (
    expense_id bigint NOT NULL,
    household_id bigint NOT NULL
);

ALTER TABLE ONLY expenses_households
    ADD CONSTRAINT expenses_households_pkey PRIMARY KEY (job_function_id, training_id);


ALTER TABLE ONLY expenses_households
    ADD CONSTRAINT expenses_households_expense_id_fkey FOREIGN KEY (expense_id) REFERENCES expenses(id);


ALTER TABLE ONLY expenses_households
    ADD CONSTRAINT expenses_households_household_id_fkey FOREIGN KEY (household_id) REFERENCES households(id);

CREATE TABLE docs (
    id bigint DEFAULT nextval('id_seq'::regclass) NOT NULL,
    name varchar(255) NOT NULL,
    content_type varchar(255) NOT NULL,
    doc bytea NOT NULL,
    version bigint DEFAULT 0 NOT NULL
);

CREATE TABLE budgets (
    id bigint DEFAULT nextval('id_seq'::regclass) NOT NULL,
    budget_type smallint NOT NULL,
    details varchar(255),
    total_sum double precision NOT NULL,
    leftover_sum double precision NOT NULL,
    version bigint DEFAULT 0 NOT NULL
);