CREATE DATABASE classifier;

GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

\connect classifier;
CREATE SCHEMA IF NOT EXISTS directory
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS directory.concert_category
(
    uuid uuid NOT NULL,
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT concert_category_pkey PRIMARY KEY (uuid),
    CONSTRAINT categorytitleconstraint UNIQUE (title)
)

    TABLESPACE pg_default;


CREATE TABLE IF NOT EXISTS directory.country
(
    uuid uuid NOT NULL,
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT country_pkey PRIMARY KEY (uuid),
    CONSTRAINT countrydescriptionconstraint UNIQUE (description),
    CONSTRAINT countrytitleconstraint UNIQUE (title)
)

    TABLESPACE pg_default;


ALTER TABLE IF EXISTS directory.concert_category
    OWNER to postgres;

ALTER TABLE IF EXISTS directory.country
    OWNER to postgres;