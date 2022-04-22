CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    city_id int
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT
);