INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('JOURNALIST'),
       ('SUBSCRIBER');

INSERT INTO users (username, password, role)
VALUES ('gloria', '12345Qwerty', 'ADMIN'),
       ('sherri', '987654321', 'JOURNALIST'),
       ('alex', '1234567890', 'SUBSCRIBER');