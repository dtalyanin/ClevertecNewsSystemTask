SELECT SETVAL('news_id_seq', (SELECT MAX(id) FROM news));
SELECT SETVAL('comments_id_seq', (SELECT MAX(id) FROM comments));