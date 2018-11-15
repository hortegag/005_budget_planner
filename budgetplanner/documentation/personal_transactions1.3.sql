-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: personal_transactions | type: DATABASE --
-- -- DROP DATABASE IF EXISTS personal_transactions;
-- CREATE DATABASE personal_transactions
-- 	ENCODING = 'UTF8';
-- -- ddl-end --
-- 

-- object: public.groups | type: TABLE --
-- DROP TABLE IF EXISTS public.groups CASCADE;
CREATE TABLE public.groups(
	id_group bigint NOT NULL,
	name varchar(50),
	description varchar(1000),
	CONSTRAINT roles_pk PRIMARY KEY (id_group)

);
-- ddl-end --
ALTER TABLE public.groups OWNER TO postgres;
-- ddl-end --

-- object: public.users_groups | type: TABLE --
-- DROP TABLE IF EXISTS public.users_groups CASCADE;
CREATE TABLE public.users_groups(
	id_group bigint,
	id_person bigint
);
-- ddl-end --
ALTER TABLE public.users_groups OWNER TO postgres;
-- ddl-end --

-- object: groups_fk | type: CONSTRAINT --
-- ALTER TABLE public.users_groups DROP CONSTRAINT IF EXISTS groups_fk CASCADE;
ALTER TABLE public.users_groups ADD CONSTRAINT groups_fk FOREIGN KEY (id_group)
REFERENCES public.groups (id_group) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.transactions | type: TABLE --
-- DROP TABLE IF EXISTS public.transactions CASCADE;
CREATE TABLE public.transactions(
	id_transaction bigint NOT NULL,
	description varchar(1000),
	current_balance numeric(30,2),
	id_transaction_type bigint,
	id_custom_transaction_type bigint,
	id_person bigint,
	CONSTRAINT transactions_pk PRIMARY KEY (id_transaction)

);
-- ddl-end --
ALTER TABLE public.transactions OWNER TO postgres;
-- ddl-end --

-- object: public.transaction_type | type: TABLE --
-- DROP TABLE IF EXISTS public.transaction_type CASCADE;
CREATE TABLE public.transaction_type(
	id_transaction_type bigint NOT NULL,
	name varchar(100),
	description varchar(1000),
	entry_type varchar(100),
	CONSTRAINT transaction_type_pk PRIMARY KEY (id_transaction_type)

);
-- ddl-end --
ALTER TABLE public.transaction_type OWNER TO postgres;
-- ddl-end --

-- object: public.custom_transaction_type | type: TABLE --
-- DROP TABLE IF EXISTS public.custom_transaction_type CASCADE;
CREATE TABLE public.custom_transaction_type(
	id_custom_transaction_type bigint NOT NULL,
	name varchar(100),
	description varchar(1000),
	entry_type varchar(100),
	CONSTRAINT custome_transaction_type_pk PRIMARY KEY (id_custom_transaction_type)

);
-- ddl-end --
ALTER TABLE public.custom_transaction_type OWNER TO postgres;
-- ddl-end --

-- object: public.people | type: TABLE --
-- DROP TABLE IF EXISTS public.people CASCADE;
CREATE TABLE public.people(
	id_person bigint NOT NULL,
	name varchar(500),
	last_name varchar(500),
	email varchar(250),
	born_date date,
	identification varchar(100),
	username varchar(500) NOT NULL,
	password varchar(500),
	enabled varchar(1),
	id_identification_type bigint,
	CONSTRAINT people_pk PRIMARY KEY (id_person),
	CONSTRAINT unique_user_ct UNIQUE (username),
	CONSTRAINT unique_email_ct UNIQUE (email)

);
-- ddl-end --
ALTER TABLE public.people OWNER TO postgres;
-- ddl-end --

-- object: public.identification_type | type: TABLE --
-- DROP TABLE IF EXISTS public.identification_type CASCADE;
CREATE TABLE public.identification_type(
	id_identification_type bigint NOT NULL,
	name varchar(100),
	mnemonic varchar(50),
	description varchar(500),
	CONSTRAINT identification_type_pk PRIMARY KEY (id_identification_type)

);
-- ddl-end --
ALTER TABLE public.identification_type OWNER TO postgres;
-- ddl-end --

-- object: public.roles | type: TABLE --
-- DROP TABLE IF EXISTS public.roles CASCADE;
CREATE TABLE public.roles(
	id_rol bigint NOT NULL,
	name varchar(100),
	description varchar(500),
	CONSTRAINT options_pk PRIMARY KEY (id_rol)

);
-- ddl-end --
ALTER TABLE public.roles OWNER TO postgres;
-- ddl-end --

-- object: public.groups_roles | type: TABLE --
-- DROP TABLE IF EXISTS public.groups_roles CASCADE;
CREATE TABLE public.groups_roles(
	id_group bigint,
	id_rol bigint
);
-- ddl-end --
ALTER TABLE public.groups_roles OWNER TO postgres;
-- ddl-end --

-- object: groups_fk | type: CONSTRAINT --
-- ALTER TABLE public.groups_roles DROP CONSTRAINT IF EXISTS groups_fk CASCADE;
ALTER TABLE public.groups_roles ADD CONSTRAINT groups_fk FOREIGN KEY (id_group)
REFERENCES public.groups (id_group) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: roles_fk | type: CONSTRAINT --
-- ALTER TABLE public.groups_roles DROP CONSTRAINT IF EXISTS roles_fk CASCADE;
ALTER TABLE public.groups_roles ADD CONSTRAINT roles_fk FOREIGN KEY (id_rol)
REFERENCES public.roles (id_rol) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: identification_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.people DROP CONSTRAINT IF EXISTS identification_type_fk CASCADE;
ALTER TABLE public.people ADD CONSTRAINT identification_type_fk FOREIGN KEY (id_identification_type)
REFERENCES public.identification_type (id_identification_type) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: people_fk | type: CONSTRAINT --
-- ALTER TABLE public.transactions DROP CONSTRAINT IF EXISTS people_fk CASCADE;
ALTER TABLE public.transactions ADD CONSTRAINT people_fk FOREIGN KEY (id_person)
REFERENCES public.people (id_person) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: transaction_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.transactions DROP CONSTRAINT IF EXISTS transaction_type_fk CASCADE;
ALTER TABLE public.transactions ADD CONSTRAINT transaction_type_fk FOREIGN KEY (id_transaction_type)
REFERENCES public.transaction_type (id_transaction_type) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: custom_transaction_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.transactions DROP CONSTRAINT IF EXISTS custom_transaction_type_fk CASCADE;
ALTER TABLE public.transactions ADD CONSTRAINT custom_transaction_type_fk FOREIGN KEY (id_custom_transaction_type)
REFERENCES public.custom_transaction_type (id_custom_transaction_type) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: people_fk | type: CONSTRAINT --
-- ALTER TABLE public.users_groups DROP CONSTRAINT IF EXISTS people_fk CASCADE;
ALTER TABLE public.users_groups ADD CONSTRAINT people_fk FOREIGN KEY (id_person)
REFERENCES public.people (id_person) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


