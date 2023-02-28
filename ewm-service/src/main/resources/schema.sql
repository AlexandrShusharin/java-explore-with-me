CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS locations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat REAL NOT NULL,
    lon REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR,
    annotation VARCHAR,
    category_id BIGINT REFERENCES categories(id),
    description VARCHAR,
    event_date TIMESTAMP WITHOUT TIME ZONE,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    location_id BIGINT REFERENCES locations(id) ON DELETE CASCADE,
    paid BOOLEAN,
    participant_limit INT,
    request_moderation BOOLEAN,
    initiator_id BIGINT REFERENCES users(id),
    state VARCHAR
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT REFERENCES events(id),
    requester_id BIGINT REFERENCES users(id),
    status VARCHAR
);

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR,
    pinned BOOLEAN
);

CREATE TABLE IF NOT EXISTS compilations_events (
    event_id BIGINT REFERENCES events(id),
    compilation_id BIGINT REFERENCES compilations(id),
    PRIMARY KEY (event_id, compilation_id)
);