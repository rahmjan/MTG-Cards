--liquibase formatted sql

--changeset jr:001
CREATE TABLE images_process
(
  operation_id UUID NOT NULL PRIMARY KEY,
  status       TEXT NOT NULL
);
