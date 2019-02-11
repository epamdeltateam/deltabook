CREATE TABLE users (
	id bigint primary key,
	login varchar(100) unique not null,
	role integer not null,
	password varchar(100) not null,
	first_name varchar(40),
	last_name varchar(40),
	picture varchar(255)
);

CREATE TABLE messages (
	id bigint primary key,
	sender_id bigint references users(id),
	recipient_id bigint references users(id),
	body varchar(255) not null,
	created_at timestamp
);

CREATE TABLE contacts (
	id bigint primary key,
	friend_from_id bigint references users(id),
	friend_to_id bigint references users(id),
	is_Accepted boolean,
	request_Message varchar(255)
);