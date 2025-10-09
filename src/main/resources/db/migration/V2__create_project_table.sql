CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    description TEXT NOT NULL,
    structure JSONB,

    constraint uq_project_name UNIQUE (name)
);