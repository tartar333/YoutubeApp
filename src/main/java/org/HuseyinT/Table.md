CREATE TYPE user_role AS ENUM ('ADMIN', 'USER' , GUEST);

CREATE TYPE like_status AS ENUM('LIKE','DISLIKE');

CREATE TYPE video_category AS ENUM('MUSIC','SPORTS','TECHNOLOGY','GAMES','ENTERTAINMENT');

CREATE TABLE IF NOT EXISTS tbluser(
id BIGSERIAL PRIMARY KEY,
name varchar(20) NOT NULL,
surname varchar(20) NOT NULL,
email varchar(50) NOT NULL UNIQUE,
username varchar(20) NOT NULL UNIQUE,
password varchar(16) NOT NULL,
role user_role NOT NULL,
state smallint DEFAULT 1,
createdAt bigint DEFAULT EXTRACT(epoch FROM now()),
updatedAt bigint DEFAULT EXTRACT(epoch FROM now()) );

CREATE TABLE IF NOT EXISTS tblvideo(
id BIGSERIAL PRIMARY KEY,
uploaderId BIGINT REFERENCES tbluser(id),
title varchar NOT NULL,
description varchar,
category video_category NOT NULL,
viewCount bigint DEFAULT 0,
likeCount bigint DEFAULT 0,
dislikeCount bigint DEFAULT 0,
state smallint DEFAULT 1,
createdAt bigint DEFAULT EXTRACT(epoch FROM now()),
updatedAt bigint DEFAULT EXTRACT(epoch FROM now()) );

CREATE TABLE IF NOT EXISTS tbllike(
id BIGSERIAL PRIMARY KEY,
likeStatus like_status NOT NULL,
videoId bigint REFERENCES tblvideo(id),
userId bigint REFERENCES tbluser(id),
state smallint DEFAULT 1,
createdAt bigint DEFAULT EXTRACT(epoch FROM now()),
updatedAt bigint DEFAULT EXTRACT(epoch FROM now()) );

CREATE TABLE IF NOT EXISTS tblcomment(
id BIGSERIAL PRIMARY KEY,
content varchar(100) NOT NULL,
videoId bigint REFERENCES tblvideo(id),
userId bigint REFERENCES tbluser(id),
state smallint DEFAULT 1,
createdAt bigint DEFAULT EXTRACT(epoch FROM now()),
updatedAt bigint DEFAULT EXTRACT(epoch FROM now()) );