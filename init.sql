-- Conectar a PostgreSQL
--psql -U postgres

-- Create database if it does not exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'dbspeedpercentulator') THEN
        CREATE DATABASE dbspeedpercentulator;
    END IF;
END
$$;

-- Conectarse a la base de datos recién creada
\c dbspeedpercentulator

-- Crear las tablas y permisos necesarios
CREATE TABLE public.call_history
(
    id serial PRIMARY KEY,
    register_date timestamp(0) with time zone,
    endpoint character varying(100),
    parameter_json character varying(100),
    response numeric(18,2),
    error text
);

-- Conceder permisos
GRANT TEMPORARY, CONNECT ON DATABASE dbspeedpercentulator TO PUBLIC;
GRANT ALL ON DATABASE dbspeedpercentulator TO postgres;
