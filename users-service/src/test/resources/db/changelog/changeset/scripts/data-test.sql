INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('JOURNALIST'),
       ('SUBSCRIBER');

INSERT INTO users (username, password, role)
VALUES ('User', '$2a$12$kdKrgZgEEmQSzgWiGs.6AOErkFP1tXvxJ.4gtnpQAVZyNmFC7cZn2', 'SUBSCRIBER'),
       ('User 2', '$2a$12$kdKrgZgEEmQSzgWiGs.6AOErkFP1tXvxJ.4gtnpQAVZyNmFC7cZn2', 'SUBSCRIBER'),
       ('User 3', '$2a$12$kdKrgZgEEmQSzgWiGs.6AOErkFP1tXvxJ.4gtnpQAVZyNmFC7cZn2', 'SUBSCRIBER'),
       ('User 4', '$2a$12$kdKrgZgEEmQSzgWiGs.6AOErkFP1tXvxJ.4gtnpQAVZyNmFC7cZn2', 'ADMIN'),
       ('User 5', '$2a$12$kdKrgZgEEmQSzgWiGs.6AOErkFP1tXvxJ.4gtnpQAVZyNmFC7cZn2', 'JOURNALIST');

