CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name varchar,
    description varchar,
    created TIMESTAMP,
    visible BOOLEAN,
    city_id int
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name varchar,
    description varchar,
    created TIMESTAMP,
    photo BYTEA
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name varchar,
  email varchar UNIQUE,
  password varchar
);