INSERT INTO news (title, text, username, created_time)
VALUES ('News', 'Text', 'User', '2023-01-01 10:00:00'),
       ('News 2', 'Text 2', 'User 2', '2023-02-01 10:00:00'),
       ('News 3', 'Text 3', 'User 3', '2023-03-01 10:00:00');

INSERT INTO comments (text, username, news_id, created_time)
VALUES ('Comment', 'User', 1, '2023-01-01 11:00:00'),
       ('Comment 2', 'User', 1, '2023-01-01 12:00:00'),
       ('Comment 3', 'User 2', 1, '2023-01-01 13:00:00'),
       ('Comment 4', 'User 3', 1, '2023-01-01 14:00:00'),
       ('Comment', 'User', 2, '2023-02-01 11:00:00'),
       ('Comment 2', 'User 2', 2, '2023-02-01 12:00:00');


