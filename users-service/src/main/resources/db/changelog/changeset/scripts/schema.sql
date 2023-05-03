CREATE TABLE roles
(
    role VARCHAR(50) PRIMARY KEY CHECK (length(role) > 0)
);

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE CHECK (length(username) > 0),
    password VARCHAR     NOT NULL CHECK (length(password) > 0),
    role     VARCHAR(50) NOT NULL REFERENCES roles (role)
);