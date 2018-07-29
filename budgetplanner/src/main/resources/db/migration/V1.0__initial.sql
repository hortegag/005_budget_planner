CREATE TABLE public.identification_type(
	id_identification_type bigint NOT NULL,
	name varchar(100),
	mnemonic varchar(50),
	description varchar(500),
	CONSTRAINT identification_type_pk PRIMARY KEY (id_identification_type)

);

insert into identification_type values (1001,'Cedula','CI','Cedula de Identidad de la persona');