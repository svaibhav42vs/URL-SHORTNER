CREATE TABLE users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE url_mapping(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    short_code VARCHAR(12) NOT NULL UNIQUE,
    original_url VARCHAR(2048) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMPTZ,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_url_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
);

CREATE INDEX idx_url_user
ON url_mapping(user_id);

CREATE INDEX idx_url_created
ON url_mapping(created_at);


CREATE TABLE click_event(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    url_id BIGINT NOT NULL,
    clicked_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    referrer VARCHAR(2048),
    user_agent VARCHAR(512),
    country VARCHAR(100),

    CONSTRAINT fk_click_url
       FOREIGN KEY (url_id)
       REFERENCES url_mapping(id)
);

CREATE INDEX idx_click_url_time
ON click_event(url_id,clicked_at);
