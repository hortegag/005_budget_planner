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
	identification varchar(100),
	username varchar(500) NOT NULL,
	password varchar(500),
	enabled varchar(1),
	id_identification_type bigint,
	CONSTRAINT people_pk PRIMARY KEY (id_person),
	CONSTRAINT unique_user_ct UNIQUE (username),
	CONSTRAINT unique_email_ct UNIQUE (email)

);

-- object: identification_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.people DROP CONSTRAINT IF EXISTS identification_type_fk CASCADE;
ALTER TABLE public.people ADD CONSTRAINT identification_type_fk FOREIGN KEY (id_identification_type)
REFERENCES public.identification_type (id_identification_type)
ON DELETE SET NULL ON UPDATE CASCADE;




insert into identification_type values (1001,'Cedula','CI','Cedula de Identidad de la persona');

insert into identification_type values (1002,'Dummy','DM','Dummy Identification');
insert into identification_type values (1003,'Pasaporte','PS','Pasaporte de la persona');


insert into people values (2003,'Hector','Ortega','hog_andy@hotmail.com','1990-01-27','0927210310','hortega','hortega','Y',1001);
insert into people values (2004,'Dummy','Smith','dsmith@hotmail.com','1989-01-27','PASS_123XYZ','dsmith','dsmith','Y',1003);

insert into people values (2005,'Homero','Hercules','hhercules@hotmail.com','1991-12-27','PASfxwYzyc','hhercules','hhercules','Y',1003);
insert into people values (2006,'RagnaK','Simons','rsimons@hotmail.com','1983-02-01','PASS_123XYYssz','rsimons','rsimons','Y',1003);

insert into people values (2007,'Lakherta','lonbeida','llombeida@hotmail.com','1984-03-03','PASS_1XrykRssz','llombeida','llombeida','Y',1003);





insert into people values (2008,'Jake','Piguave','jpiguave@hotmail.com','1983-01-27','PASS_1234XYZ','jpiguave','jpiguave','Y',1003);
insert into people values (2009,'Xavier','Chucuca','xchuchuca@hotmail.com','1995-12-27','PASf4xwYzyc','xchuchuca','xchuchuca','Y',1003);
insert into people values (2010,'Ney','Díaz','ndiaz@hotmail.com','1980-02-01','PASS_1234XYYssz','ndiaz','ndiaz','Y',1003);
insert into people values (2011,'Sol','Domenech','smoenech@hotmail.com','1983-03-03','PASS_14XrykRssz','sdomenech','sdomenech','Y',1003);

insert into people values (2012,'Danny','Tevez','dtevez@hotmail.com','1979-01-27','PASS_1235XYZ','dtevez','dtevez','Y',1003);
insert into people values (2013,'Emy','Pereira','epereira@hotmail.com','1980-12-27','PASf5xwYzyc','epereira','epereira','Y',1003);
insert into people values (2014,'Catalina','Jhonson','cjhonson@hotmail.com','1981-02-01','PASS_1235XYYssz','cjhonson','cjhonson','Y',1003);
insert into people values (2015,'Natasha','Larson','nlarson@hotmail.com','1982-03-03','PASS_15XrykRssz','nlarson','nlarson','Y',1003);




CREATE TABLE public.groups(
	id_group bigint NOT NULL,
	name varchar(50),
	description varchar(1000),
	CONSTRAINT roles_pk PRIMARY KEY (id_group)

);

CREATE TABLE public.users_groups(
	id_user_group bigint NOT NULL,
	id_group bigint,
	id_person bigint,
	CONSTRAINT users_groups_pk PRIMARY KEY (id_user_group)

);

-- object: groups_fk | type: CONSTRAINT --
-- ALTER TABLE public.users_groups DROP CONSTRAINT IF EXISTS groups_fk CASCADE;
ALTER TABLE public.users_groups ADD CONSTRAINT groups_fk FOREIGN KEY (id_group)
REFERENCES public.groups (id_group) 
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: public.roles | type: TABLE --
-- DROP TABLE IF EXISTS public.roles CASCADE;
CREATE TABLE public.roles(
	id_rol smallint NOT NULL,
	name varchar(100),
	description varchar(500),
	CONSTRAINT options_pk PRIMARY KEY (id_rol)

);


-- object: public.groups_roles | type: TABLE --
-- DROP TABLE IF EXISTS public.groups_roles CASCADE;
CREATE TABLE public.groups_roles(
	id_group_rol bigint,
	id_group bigint,
	id_rol smallint
);


-- object: roles_fk | type: CONSTRAINT --
-- ALTER TABLE public.groups_roles DROP CONSTRAINT IF EXISTS roles_fk CASCADE;
ALTER TABLE public.groups_roles ADD CONSTRAINT roles_fk FOREIGN KEY (id_rol)
REFERENCES public.roles (id_rol) 
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --
