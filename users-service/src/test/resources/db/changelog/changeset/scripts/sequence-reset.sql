SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));