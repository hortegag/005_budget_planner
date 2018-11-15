-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: liga_test | type: DATABASE --
-- -- DROP DATABASE IF EXISTS liga_test;
-- CREATE DATABASE liga_test
-- 	ENCODING = 'UTF8';
-- -- ddl-end --
-- 

-- object: public.usuarios | type: TABLE --
-- DROP TABLE IF EXISTS public.usuarios CASCADE;
CREATE TABLE public.usuarios(
	id_usuario bigint NOT NULL,
	nombre smallint,
	clave smallint,
	CONSTRAINT usuarios_pk PRIMARY KEY (id_usuario)

);
-- ddl-end --
ALTER TABLE public.usuarios OWNER TO postgres;
-- ddl-end --

-- object: public.roles | type: TABLE --
-- DROP TABLE IF EXISTS public.roles CASCADE;
CREATE TABLE public.roles(
	id_rol bigint NOT NULL,
	nombre varchar(50),
	descripcion varchar(1000),
	CONSTRAINT roles_pk PRIMARY KEY (id_rol)

);
-- ddl-end --
ALTER TABLE public.roles OWNER TO postgres;
-- ddl-end --

-- object: public.usuarios_roles | type: TABLE --
-- DROP TABLE IF EXISTS public.usuarios_roles CASCADE;
CREATE TABLE public.usuarios_roles(
	id_permiso smallint,
	id_usuario bigint,
	id_rol bigint
);
-- ddl-end --
ALTER TABLE public.usuarios_roles OWNER TO postgres;
-- ddl-end --

-- object: usuarios_fk | type: CONSTRAINT --
-- ALTER TABLE public.usuarios_roles DROP CONSTRAINT IF EXISTS usuarios_fk CASCADE;
ALTER TABLE public.usuarios_roles ADD CONSTRAINT usuarios_fk FOREIGN KEY (id_usuario)
REFERENCES public.usuarios (id_usuario) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: roles_fk | type: CONSTRAINT --
-- ALTER TABLE public.usuarios_roles DROP CONSTRAINT IF EXISTS roles_fk CASCADE;
ALTER TABLE public.usuarios_roles ADD CONSTRAINT roles_fk FOREIGN KEY (id_rol)
REFERENCES public.roles (id_rol) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


