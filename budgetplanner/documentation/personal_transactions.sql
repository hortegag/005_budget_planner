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

-- object: public.users | type: TABLE --
-- DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE public.users(
	id_user bigint NOT NULL,
	name varchar(100),
	password varchar(100),
	id_person bigint,
	CONSTRAINT usuarios_pk PRIMARY KEY (id_user)

);
-- ddl-end --
ALTER TABLE public.users OWNER TO postgres;
-- ddl-end --

-- object: public.roles | type: TABLE --
-- DROP TABLE IF EXISTS public.roles CASCADE;
CREATE TABLE public.roles(
	id_rol bigint NOT NULL,
	name varchar(50),
	description varchar(1000),
	CONSTRAINT roles_pk PRIMARY KEY (id_rol)

);
-- ddl-end --
ALTER TABLE public.roles OWNER TO postgres;
-- ddl-end --

-- object: public.usuarios_roles | type: TABLE --
-- DROP TABLE IF EXISTS public.usuarios_roles CASCADE;
CREATE TABLE public.usuarios_roles(
	id_user_rol bigint,
	id_rol bigint,
	id_user bigint
);
-- ddl-end --
ALTER TABLE public.usuarios_roles OWNER TO postgres;
-- ddl-end --

-- object: users_fk | type: CONSTRAINT --
-- ALTER TABLE public.usuarios_roles DROP CONSTRAINT IF EXISTS users_fk CASCADE;
ALTER TABLE public.usuarios_roles ADD CONSTRAINT users_fk FOREIGN KEY (id_user)
REFERENCES public.users (id_user) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: roles_fk | type: CONSTRAINT --
-- ALTER TABLE public.usuarios_roles DROP CONSTRAINT IF EXISTS roles_fk CASCADE;
ALTER TABLE public.usuarios_roles ADD CONSTRAINT roles_fk FOREIGN KEY (id_rol)
REFERENCES public.roles (id_rol) MATCH FULL
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

-- object: public.custome_transaction_type | type: TABLE --
-- DROP TABLE IF EXISTS public.custome_transaction_type CASCADE;
CREATE TABLE public.custome_transaction_type(
	id_custom_transaction_type bigint NOT NULL,
	name varchar(100),
	description varchar(1000),
	entry_type varchar(100),
	CONSTRAINT custome_transaction_type_pk PRIMARY KEY (id_custom_transaction_type)

);
-- ddl-end --
ALTER TABLE public.custome_transaction_type OWNER TO postgres;
-- ddl-end --

-- object: public.people | type: TABLE --
-- DROP TABLE IF EXISTS public.people CASCADE;
CREATE TABLE public.people(
	id_person bigint NOT NULL,
	name varchar(500),
	last_name varchar(500),
	email varchar(250),
	born_date date,
	indentification varchar(100),
	id_identification_type bigint,
	CONSTRAINT people_pk PRIMARY KEY (id_person)

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

-- object: public.options | type: TABLE --
-- DROP TABLE IF EXISTS public.options CASCADE;
CREATE TABLE public.options(
	id_option smallint NOT NULL,
	name varchar(100),
	description varchar(500),
	CONSTRAINT options_pk PRIMARY KEY (id_option)

);
-- ddl-end --
ALTER TABLE public.options OWNER TO postgres;
-- ddl-end --

-- object: public.roles_options | type: TABLE --
-- DROP TABLE IF EXISTS public.roles_options CASCADE;
CREATE TABLE public.roles_options(
	id_option_rol bigint,
	id_rol bigint,
	id_option smallint
);
-- ddl-end --
ALTER TABLE public.roles_options OWNER TO postgres;
-- ddl-end --

-- object: roles_fk | type: CONSTRAINT --
-- ALTER TABLE public.roles_options DROP CONSTRAINT IF EXISTS roles_fk CASCADE;
ALTER TABLE public.roles_options ADD CONSTRAINT roles_fk FOREIGN KEY (id_rol)
REFERENCES public.roles (id_rol) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: options_fk | type: CONSTRAINT --
-- ALTER TABLE public.roles_options DROP CONSTRAINT IF EXISTS options_fk CASCADE;
ALTER TABLE public.roles_options ADD CONSTRAINT options_fk FOREIGN KEY (id_option)
REFERENCES public.options (id_option) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: identification_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.people DROP CONSTRAINT IF EXISTS identification_type_fk CASCADE;
ALTER TABLE public.people ADD CONSTRAINT identification_type_fk FOREIGN KEY (id_identification_type)
REFERENCES public.identification_type (id_identification_type) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: people_fk | type: CONSTRAINT --
-- ALTER TABLE public.users DROP CONSTRAINT IF EXISTS people_fk CASCADE;
ALTER TABLE public.users ADD CONSTRAINT people_fk FOREIGN KEY (id_person)
REFERENCES public.people (id_person) MATCH FULL
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

-- object: custome_transaction_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.transactions DROP CONSTRAINT IF EXISTS custome_transaction_type_fk CASCADE;
ALTER TABLE public.transactions ADD CONSTRAINT custome_transaction_type_fk FOREIGN KEY (id_custom_transaction_type)
REFERENCES public.custome_transaction_type (id_custom_transaction_type) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


