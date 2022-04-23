CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    city_id int
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name TEXT,
  email varchar UNIQUE,
  password TEXT
);