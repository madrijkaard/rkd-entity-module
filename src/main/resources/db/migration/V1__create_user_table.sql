CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    email VARCHAR(255) NOT NULL,
    hash TEXT NOT NULL,
    salt TEXT NOT NULL,
    structure JSONB,

    CONSTRAINT uq_user_name UNIQUE (name),
    CONSTRAINT uq_user_email UNIQUE (email)
);