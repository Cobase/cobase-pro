# --- !Ups

create type role as enum ('admin', 'user');

alter table "users" add column "role" role;

update users set role = 'user';

# --- !Downs

alter table "users" drop column "role";