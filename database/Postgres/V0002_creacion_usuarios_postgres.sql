CREATE ROLE logistica_app
    WITH
    LOGIN
    PASSWORD '**************';

GRANT CONNECT ON DATABASE neondb TO logistica_app;

GRANT USAGE ON SCHEMA public TO logistica_app;

GRANT SELECT, INSERT, UPDATE, DELETE
      ON ALL TABLES IN SCHEMA public
          TO logistica_app;

GRANT USAGE, SELECT
             ON ALL SEQUENCES IN SCHEMA public
                 TO logistica_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES
    TO logistica_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT USAGE, SELECT ON SEQUENCES
    TO logistica_app;