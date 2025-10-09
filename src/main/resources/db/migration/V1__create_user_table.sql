CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    email VARCHAR(255) NOT NULL,
    hash VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    structure JSONB,

    CONSTRAINT uq_user_name UNIQUE (name),
    CONSTRAINT uq_user_email UNIQUE (email)
);