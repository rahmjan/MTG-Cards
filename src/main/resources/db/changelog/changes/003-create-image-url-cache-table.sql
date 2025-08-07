--liquibase formatted sql

--changeset jr:003
CREATE TABLE image_url_cache
(
  name    TEXT NOT NULL PRIMARY KEY,
  png_url TEXT NOT NULL
);
