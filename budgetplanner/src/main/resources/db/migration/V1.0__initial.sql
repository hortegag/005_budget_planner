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
	current_balance numeric(30,2),
	income numeric(30,2),
	expense numeric(30,2),
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




insert into identification_type values (1001,'Identification','IC','Identification Card of the person');

insert into identification_type values (1002,'Dummy','DM','Dummy Identification');
insert into identification_type values (1003,'Passport','PS','Pasaporte de la persona');


insert into people values (2003,'Hector','Ortega','hog_andy@hotmail.com','1990-01-27','0927210310','hortega','$2a$10$MgOjhHkZ3/jeFokibgVcP.MYEEF5Bkph/WiZ5lyTipewPKfwgsxrm','Y', 295, 300, 5,1001);
insert into people values (2004,'Dummy','Smith','dsmith@hotmail.com','1989-01-27','PASS_123XYZ','dsmith','$2a$10$d4HxrYDWhHt7bQaL914PluHCy1MH6B6kWiQ1sc8wbV7.ivK7tL/SO','Y', 0, 0, 0,1003);

insert into people values (2005,'Homero','Hercules','hhercules@hotmail.com','1991-12-27','PASfxwYzyc','hhercules','hhercules','Y', 0, 0, 0, 1003);
insert into people values (2006,'RagnaK','Simons','rsimons@hotmail.com','1983-02-01','PASS_123XYYssz','rsimons','rsimons','Y',0, 0, 0, 1003);

insert into people values (2007,'Lakherta','lonbeida','llombeida@hotmail.com','1984-03-03','PASS_1XrykRssz','llombeida','llombeida','Y', 0, 0, 0, 1003);





insert into people values (2008,'Jake','Piguave','jpiguave@hotmail.com','1983-01-27','PASS_1234XYZ','jpiguave','jpiguave','Y', 0, 0, 0, 1003);
insert into people values (2009,'Xavier','Chucuca','xchuchuca@hotmail.com','1995-12-27','PASf4xwYzyc','xchuchuca','xchuchuca','Y',  0, 0, 0, 1003);
insert into people values (2010,'Ney','Díaz','ndiaz@hotmail.com','1980-02-01','PASS_1234XYYssz','ndiaz','ndiaz','Y',  0, 0, 0, 1003);
insert into people values (2011,'Sol','Domenech','smoenech@hotmail.com','1983-03-03','PASS_14XrykRssz','sdomenech','sdomenech','Y', 0, 0, 0, 1003);

insert into people values (2012,'Danny','Tevez','dtevez@hotmail.com','1979-01-27','PASS_1235XYZ','dtevez','dtevez','Y', 0, 0, 0, 1003);
insert into people values (2013,'Emy','Pereira','epereira@hotmail.com','1980-12-27','PASf5xwYzyc','epereira','epereira','Y', 0, 0, 0, 1003);
insert into people values (2014,'Catalina','Jhonson','cjhonson@hotmail.com','1981-02-01','PASS_1235XYYssz','cjhonson','cjhonson','Y',  0, 0, 0, 1003);
insert into people values (2015,'Natasha','Larson','nlarson@hotmail.com','1982-03-03','PASS_15XrykRssz','nlarson','nlarson','Y', 0, 0, 0, 1003);




CREATE TABLE public.groups(
	id_group bigint NOT NULL,
	name varchar(50),
	description varchar(1000),
	CONSTRAINT roles_pk PRIMARY KEY (id_group)

);

CREATE TABLE public.users_groups(
	id_group bigint,
	id_person bigint
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
	id_rol bigint NOT NULL,
	name varchar(100),
	description varchar(500),
	CONSTRAINT options_pk PRIMARY KEY (id_rol)

);


-- object: public.groups_roles | type: TABLE --
-- DROP TABLE IF EXISTS public.groups_roles CASCADE;
CREATE TABLE public.groups_roles(
	id_group bigint,
	id_rol bigint
);

-- object: groups_fk | type: CONSTRAINT --
-- ALTER TABLE public.groups_roles DROP CONSTRAINT IF EXISTS groups_fk CASCADE;
ALTER TABLE public.groups_roles ADD CONSTRAINT groups_roles_group_fk FOREIGN KEY (id_group)
REFERENCES public.groups (id_group) 
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: roles_fk | type: CONSTRAINT --
-- ALTER TABLE public.groups_roles DROP CONSTRAINT IF EXISTS roles_fk CASCADE;
ALTER TABLE public.groups_roles ADD CONSTRAINT roles_fk FOREIGN KEY (id_rol)
REFERENCES public.roles (id_rol) 
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


insert into groups values (4000,'USERS','Users of the application');
insert into groups values (4001,'ADMINS','Admins of the application');

insert into roles values (5000,'ROLE_USER','Role of the users ');
insert into roles values (5001,'ROLE_ADMIN','Role of the administrators');

insert into groups_roles values (4000,5000);
insert into groups_roles values (4001,5001);

--dsmith
insert into users_groups values (4000,2004);
insert into users_groups values (4001,2004);

--hortega
insert into users_groups values (4001,2003);




-- object: public.transactions | type: TABLE --
-- DROP TABLE IF EXISTS public.transactions CASCADE;
CREATE TABLE public.transactions(
	id_transaction bigint NOT NULL,
	transaction_date date,
	description varchar(1000),
	current_balance numeric(30,2),
	value numeric(30,2),
	id_transaction_type bigint,
	id_custom_transaction_type bigint,
	id_person bigint,
	CONSTRAINT transactions_pk PRIMARY KEY (id_transaction)

);

-- object: public.custom_transaction_type | type: TABLE --
-- DROP TABLE IF EXISTS public.custom_transaction_type CASCADE;
CREATE TABLE public.custom_transaction_type(
	id_custom_transaction_type bigint NOT NULL,
	name varchar(100),
	description varchar(1000),
	entry_type varchar(100),
	CONSTRAINT custome_transaction_type_pk PRIMARY KEY (id_custom_transaction_type)

);


-- object: public.transaction_type | type: TABLE --
-- DROP TABLE IF EXISTS public.transaction_type CASCADE;
CREATE TABLE public.transaction_type(
	id_transaction_type bigint NOT NULL,
	name varchar(100),
	description varchar(1000),
	entry_type varchar(100),
	CONSTRAINT transaction_type_pk PRIMARY KEY (id_transaction_type)

);


-- object: people_fk | type: CONSTRAINT --
-- ALTER TABLE public.transactions DROP CONSTRAINT IF EXISTS people_fk CASCADE;
ALTER TABLE public.transactions ADD CONSTRAINT people_fk FOREIGN KEY (id_person)
REFERENCES public.people (id_person) 
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: transaction_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.transactions DROP CONSTRAINT IF EXISTS transaction_type_fk CASCADE;
ALTER TABLE public.transactions ADD CONSTRAINT transaction_type_fk FOREIGN KEY (id_transaction_type)
REFERENCES public.transaction_type (id_transaction_type) 
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: custom_transaction_type_fk | type: CONSTRAINT --
-- ALTER TABLE public.transactions DROP CONSTRAINT IF EXISTS custom_transaction_type_fk CASCADE;
ALTER TABLE public.transactions ADD CONSTRAINT custom_transaction_type_fk FOREIGN KEY (id_custom_transaction_type)
REFERENCES public.custom_transaction_type (id_custom_transaction_type)
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



insert into transaction_type values (6001, 'SALARY', 'Salary earned this month', 'CREDIT');
insert into transaction_type values (6002, 'FOOD', 'Money spent on food', 'DEBIT');
insert into transaction_type values (6003, 'ENTERTEINMENT', 'Money spent on enterteinment', 'DEBIT');


insert into transactions values (7001, '1983-02-01', 'SALARY PAYMENT', 300, 300, 6001, null, 2003);
insert into transactions values (7002, '1983-02-02', 'LUNCH SUBMARINE', 255, 5, 6002, null, 2003);






