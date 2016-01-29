# --- !Ups

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Users
alter table "users"
  alter column "email" set not null,
  drop column "full_name",
  add column "password" varchar,
  add column "created" timestamp,
  add column "verification_token" varchar,
  add column "verified" timestamp,
  add column "password_reset_token" varchar,
  add column "password_reset_requested" timestamp,
  add column "password_reset_used" timestamp;

update users as u
  set password = pi.password
  from user_login_infos uli
  inner join password_infos pi on uli.login_info_id = pi.login_info_id
  where u.id = uli.user_id;

update users set created = now();

CREATE OR REPLACE FUNCTION set_verification_tokens() RETURNS void AS $$
DECLARE
  u RECORD;;
BEGIN
  FOR u IN SELECT * FROM users LOOP
    UPDATE users
      SET
        verification_token = encode(digest(random()::text, 'sha1'), 'hex'),
        verified = NOW()
      WHERE id = u.id;;
  END LOOP;;
END;;
$$ LANGUAGE plpgsql;

SELECT set_verification_tokens();
DROP FUNCTION set_verification_tokens();

alter table "users"
  alter column "password" set not null,
  alter column "created" set not null,
  alter column "verification_token" set not null;

create unique index "users_email_uniq" on "users" ("email");
create unique index "users_password_reset_token_uniq" on "users" ("password_reset_token");
create unique index "users_verification_token_uniq" on "users" ("verification_token");

alter table "users" alter id type uuid using id::uuid;


-- Authentication tokens
create table "authentication_tokens" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "user_id" UUID NOT NULL,
  "token" VARCHAR NOT NULL,
  "created" TIMESTAMP NOT NULL
);
create unique index "authentication_tokens_token_uniq" on "authentication_tokens" ("token");
alter table "authentication_tokens"
  add constraint "user_fk"
  foreign key("user_id") references "users"("id")
  on update cascade
  on delete cascade;


-- Posts
alter table "posts" add column "created" timestamp;
update "posts" set created = to_timestamp(created_timestamp / 1000);

alter table "posts"
  alter column "created" set not null,
  drop column "created_timestamp";

alter table "posts" add constraint "group_fk"
  foreign key("group_id") references "groups"("id")
  on update cascade
  on delete cascade;

drop index "idx_post_group";


-- Groups
drop index "idx_group_id";


-- Subscriptions
drop index "idx_subscr_group";
drop index "idx_subscr_user";

alter table "subscriptions"
  add constraint "group_fk" foreign key("group_id")
  references "groups"("id")
  on update cascade
  on delete cascade;

alter table "subscriptions"
  add constraint "user_fk" foreign key("user_id")
  references "users"("id")
  on update cascade
  on delete cascade;



-- Drop old tables
drop table "login_infos";
drop table "oauth1_infos";
drop table "oauth2_infos";
drop table "openid_attributes";
drop table "openid_infos";
drop table "password_infos";
drop table "user_login_infos";
