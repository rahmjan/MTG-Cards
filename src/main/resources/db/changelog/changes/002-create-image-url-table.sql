--liquibase formatted sql

--changeset jr:002
CREATE TABLE image_url
(
  id           UUID NOT NULL PRIMARY KEY,
  operation_id UUID NOT NULL,
  name         TEXT NOT NULL,
  png_url      TEXT,
  error        TEXT
);

CREATE INDEX idx_image_url_operation_id ON image_url (operation_id);
