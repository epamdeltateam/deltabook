-- Login admin2; PW - admin2

--PW - admin98
UPDATE USERS SET LOGIN='wale' WHERE LOGIN='nagibator98';
-- alex0407
UPDATE USERS SET LOGIN='liquviant', FIRST_NAME='Alexey', LAST_NAME='Shilov' WHERE LOGIN='dd04072001';
-- fedor78
UPDATE USERS SET LOGIN='biritt', LAST_NAME='Mishin' WHERE LOGIN='fedor_traktorist';
-- chichik78
UPDATE USERS SET LOGIN='igor', LAST_NAME='Bogdanov' WHERE LOGIN='selo78';

UPDATE CONTACTS SET REQUEST_MESSAGE='Привет. Мы с тобой в одной группе учились.' WHERE ID='1';
UPDATE CONTACTS SET REQUEST_MESSAGE='Хей, мы на рыбалке познакомились вчера.' WHERE ID='2';
UPDATE CONTACTS SET REQUEST_MESSAGE='Взаимное добавление в друзья' WHERE ID='3';
UPDATE CONTACTS SET REQUEST_MESSAGE='Коллега, мы работаем в одной исследовательской группе' WHERE ID='4';
UPDATE CONTACTS SET REQUEST_MESSAGE='Двоюродный брат твоего брата' WHERE ID='5';

UPDATE MESSAGES SET BODY='Привет. Это же взаимное добавление в друзья? Почему ты меня удалил?', SENDER_ID='5' WHERE ID='1';
UPDATE MESSAGES SET BODY='Не подскажешь, где купить книгу для начинающего рыболова?' WHERE ID='2';
UPDATE MESSAGES SET BODY='Да, и еще мне нужна удочка. Не продашь по дешевке?' WHERE ID='3';
UPDATE MESSAGES SET BODY='Достигли ли вы какого-либо успеха в вашем исследовании?' WHERE ID='4';
UPDATE MESSAGES SET BODY='Привет, Игорь. Как поживаешь? ' WHERE ID='5';

