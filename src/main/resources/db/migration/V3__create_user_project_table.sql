CREATE TABLE user_project (
    user_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_user_project PRIMARY KEY (user_id, project_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_project_user_id ON user_project(user_id);
CREATE INDEX idx_user_project_project_id ON user_project(project_id);