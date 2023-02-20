--liquibase formatted sql

--changeset grisha:1
CREATE TABLE IF NOT EXISTS signed.roles
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT rolenameconstraint UNIQUE (name)
)

    TABLESPACE pg_default;

--changeset grisha:2
CREATE TABLE IF NOT EXISTS signed.users
(
    uuid uuid NOT NULL,
    create_date timestamp(3) without time zone,
    update_date timestamp(3) without time zone,
    mail character varying(255) COLLATE pg_catalog."default",
    nick character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (uuid),
    CONSTRAINT emailconstraint UNIQUE (mail),
    CONSTRAINT nickconstraint UNIQUE (nick)
)

    TABLESPACE pg_default;

--changeset grisha:3
CREATE TABLE IF NOT EXISTS signed.user_roles
(
    user_uuid uuid NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_uuid, role_id),
    CONSTRAINT fkb4bms60ebskkrd05297us35x9 FOREIGN KEY (user_uuid)
        REFERENCES signed.users (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
        REFERENCES signed.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

--changeset grisha:4
ALTER TABLE IF EXISTS signed.roles
    OWNER to postgres;

ALTER TABLE IF EXISTS signed.user_roles
    OWNER to postgres;

ALTER TABLE IF EXISTS signed.users
    OWNER to postgres;


