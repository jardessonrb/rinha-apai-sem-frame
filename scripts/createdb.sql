CREATE DATABASE rinha23db;

CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    apelido VARCHAR(32),
    nascimento DATE,
    termo_busca TEXT,
    stacks TEXT,
    uuid UUID
);