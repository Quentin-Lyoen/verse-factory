CREATE SCHEMA IF NOT EXISTS versefactory;

CREATE TABLE versefactory.example (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO versefactory.example (id, name)
VALUES (1, 'Un exemple');