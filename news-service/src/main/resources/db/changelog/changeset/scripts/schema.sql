CREATE TABLE news
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(200) NOT NULL CHECK (length(title) > 0),
    text         VARCHAR      NOT NULL CHECK (length(text) > 0),
    username   VARCHAR(50)  NOT NULL CHECK (length(username) > 0),
    created_time TIMESTAMP    NOT NULL
);

CREATE TABLE comments
(
    id           BIGSERIAL PRIMARY KEY,
    text         VARCHAR     NOT NULL CHECK (length(text) > 0),
    username    VARCHAR(50) NOT NULL CHECK (length(username) > 0),
    news_id      BIGINT      NOT NULL REFERENCES news (id),
    created_time TIMESTAMP   NOT NULL
);
