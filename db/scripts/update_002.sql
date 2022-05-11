CREATE TABLE if not exists post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    visible BOOLEAN,
    city_id int
);

CREATE TABLE if not exists candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    photo BYTEA
);

CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  name TEXT,
  email varchar UNIQUE,
  password TEXT
);