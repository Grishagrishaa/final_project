CREATE DATABASE afisha_service;

GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

\connect afisha_service;
CREATE SCHEMA IF NOT EXISTS events
    AUTHORIZATION postgres;


CREATE TABLE IF NOT EXISTS events.concerts
(
    uuid uuid NOT NULL,
    author character varying(255) COLLATE pg_catalog."default",
    create_date timestamp(3) without time zone,
    date_end_of_sale timestamp(3) without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    event_date timestamp(3) without time zone,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    update_date timestamp(3) without time zone,
    category uuid NOT NULL,
    CONSTRAINT concerts_pkey PRIMARY KEY (uuid)
)
    TABLESPACE pg_default;


CREATE TABLE IF NOT EXISTS events.films
(
    uuid uuid NOT NULL,
    author character varying(255) COLLATE pg_catalog."default",
    create_date timestamp(3) without time zone,
    date_end_of_sale timestamp(3) without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    event_date timestamp without time zone,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    update_date timestamp(3) without time zone,
    country uuid,
    duration integer,
    release_date timestamp without time zone,
    release_year integer,
    CONSTRAINT films_pkey PRIMARY KEY (uuid)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS events.films
    OWNER to postgres;

ALTER TABLE IF EXISTS events.concerts
    OWNER to postgres;