INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('JOURNALIST'),
       ('SUBSCRIBER');

INSERT INTO users (username, password, role)
VALUES ('gloria', '$2a$12$m.E7AraOL/4oxe2QmdLTu.M3ByR6TLQbL562FlxiL/5CrYA4lA/Ie', 'ADMIN'),
       ('sherri', '$2a$12$q1WJFkI5oNt56CoBL/t16.zTkMlqmelkJO2xeNPn2QKuJvziMm9GG', 'JOURNALIST'),
       ('alex', '$2a$12$rJbFk5oMKXbKPUqORQ640.sxEbix8g6nZHIoi/CqswHHLSR.d/jj6', 'SUBSCRIBER');