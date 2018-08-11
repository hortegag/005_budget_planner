--hibernate use by default to generate the id of the of the entities
CREATE SEQUENCE hibernate_sequence INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;


CREATE TABLE public.identification_type(
	id_identification_type bigint NOT NULL,
	name varchar(100),
	mnemonic varchar(50),
	description varchar(500),
	CONSTRAINT identification_type_pk PRIMARY KEY (id_identification_type)

);




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

-- object: identification_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.people DROP CONSTRAINT IF EXISTS identification_type_fk CASCADE;
ALTER TABLE public.people ADD CONSTRAINT identification_type_fk FOREIGN KEY (id_identification_type)
REFERENCES public.identification_type (id_identification_type)
ON DELETE SET NULL ON UPDATE CASCADE;




insert into identification_type values (1001,'Cedula','CI','Cedula de Identidad de la persona');

insert into identification_type values (1002,'Dummy','DM','Dummy Identification');
insert into identification_type values (1003,'Pasaporte','PS','Pasaporte de la persona');


insert into people values (2003,'Hector','Ortega','hog_andy@hotmail.com','1990-01-27','0927210310',1001);
insert into people values (2004,'Dummy','Smith','dsmith@hotmail.com','1989-01-27','PASS_123XYZ',1003);





