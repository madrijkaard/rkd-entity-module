CREATE SEQUENCE IF NOT EXISTS revinfo_seq START 1;

CREATE TABLE IF NOT EXISTS revinfo (
    rev BIGINT PRIMARY KEY DEFAULT nextval('revinfo_seq'),
    revtstmp BIGINT
);
