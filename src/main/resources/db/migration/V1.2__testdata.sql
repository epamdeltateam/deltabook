INSERT INTO USERS (LOGIN, ROLE, PASSWORD, FIRST_NAME, LAST_NAME) VALUES ('admin2', 0, 'admin', 'ivan', 'ivanov');
INSERT INTO USERS (LOGIN, ROLE, PASSWORD, FIRST_NAME, LAST_NAME) VALUES ('nagibator98', 1, 'admin98', 'dmitry', 'sidorov');
INSERT INTO USERS (LOGIN, ROLE, PASSWORD, FIRST_NAME, LAST_NAME) VALUES ('dd04072001', 1, 'alex0407', 'alex', 'wolf');
INSERT INTO USERS (LOGIN, ROLE, PASSWORD, FIRST_NAME, LAST_NAME) VALUES ('fedor_traktorist', 1, 'fedor78', 'fedor', 'ignatev');
INSERT INTO USERS (LOGIN, ROLE, PASSWORD, FIRST_NAME, LAST_NAME) VALUES ('selo78', 1, 'chichik78', 'anton', 'chichikov');

INSERT INTO CONTACTS(FRIEND_FROM_ID, FRIEND_TO_ID, IS_ACCEPTED, REQUEST_MESSAGE) VALUES (1, 2, false, 'приффки');
INSERT INTO CONTACTS(FRIEND_FROM_ID, FRIEND_TO_ID, IS_ACCEPTED, REQUEST_MESSAGE) VALUES (2, 3, false, 'хей чувак');
INSERT INTO CONTACTS(FRIEND_FROM_ID, FRIEND_TO_ID, IS_ACCEPTED, REQUEST_MESSAGE) VALUES (3, 1, false, 'напиши строчна');
INSERT INTO CONTACTS(FRIEND_FROM_ID, FRIEND_TO_ID, IS_ACCEPTED, REQUEST_MESSAGE) VALUES (2, 4, false, 'займи косарь плиз');
INSERT INTO CONTACTS(FRIEND_FROM_ID, FRIEND_TO_ID, IS_ACCEPTED, REQUEST_MESSAGE) VALUES (5, 1, false, 'встреча завтра');

INSERT INTO MESSAGES(SENDER_ID,RECIPIENT_ID, BODY, CREATED_AT) VALUES (2, 1, 'досвидос', '2019-02-13 17:03:07');
INSERT INTO MESSAGES(SENDER_ID,RECIPIENT_ID, BODY, CREATED_AT) VALUES (3, 2, 'дарова', '2019-02-13 17:08:15');
INSERT INTO MESSAGES(SENDER_ID,RECIPIENT_ID, BODY, CREATED_AT) VALUES (3, 2, 'что хотел?', '2019-02-13 17:59:13');
INSERT INTO MESSAGES(SENDER_ID,RECIPIENT_ID, BODY, CREATED_AT) VALUES (4, 2, 'ты мне прошлый займ не вернул', '2019-02-13 18:57:11');
INSERT INTO MESSAGES(SENDER_ID,RECIPIENT_ID, BODY, CREATED_AT) VALUES (1, 5, 'на память не жалуюсь', '2019-02-13 18:59:45');