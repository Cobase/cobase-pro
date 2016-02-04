# --- !Ups

alter table "users" add column "role" varchar;

update users set role = 'user';

-- Drop old tables
alter table "users" drop column "role";

