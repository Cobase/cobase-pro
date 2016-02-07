# --- !Ups

alter table "users" drop column if exists "role";

alter table "users" add column "role" varchar;

update users set role = 'user';

# --- !Downs

alter table "users" drop column "role";

