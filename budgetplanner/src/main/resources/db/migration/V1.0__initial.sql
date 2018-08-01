--hibernate use by default to generate the id of the of the entities
CREATE SEQUENCE hibernate_sequence INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;


CREATE TABLE public.identification_type(
	id_identification_type bigint NOT NULL,
	name varchar(100),
	mnemonic varchar(50),
	description varchar(500),
	CONSTRAINT identification_type_pk PRIMARY KEY (id_identification_type)

);

insert into identification_type values (1001,'Cedula','CI','Cedula de Identidad de la persona');

insert into identification_type values (1002,'Dummy','DM','Dummy Identification');
insert into identification_type values (1003,'Pasaporte','PS','Pasaporte de la persona');