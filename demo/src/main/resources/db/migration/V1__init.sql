CREATE TABLE users (
	id bigint auto_increment primary key,
	login varchar(100) unique not null,
	role integer not null,
	password varchar(100) not null,
	first_name varchar(40),
	last_name varchar(40),
	picture clob
);

CREATE TABLE messages (
	id bigint auto_increment primary key,
	sender_id bigint references users(id),
	recipient_id bigint references users(id),
	body clob not null,
	created_at timestamp
);

CREATE TABLE contacts (
	id bigint auto_increment primary key,
	friend_from_id bigint references users(id),
	friend_to_id bigint references users(id),
	is_Accepted boolean,
	request_Message clob
);