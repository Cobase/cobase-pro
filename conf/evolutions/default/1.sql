# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "groups" ("id" UUID NOT NULL PRIMARY KEY,"title" VARCHAR(254) NOT NULL,"tags" VARCHAR(254) NOT NULL);
create unique index "idx_group_id" on "groups" ("id");
create table "login_infos" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"provider_id" VARCHAR(254) NOT NULL,"provider_key" VARCHAR(254) NOT NULL);
create table "oauth1_infos" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"token" VARCHAR(254) NOT NULL,"secret" VARCHAR(254) NOT NULL,"logininfo_id" BIGINT NOT NULL);
create table "oauth2_infos" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"access_token" VARCHAR(254) NOT NULL,"token_type" VARCHAR(254),"expires_in" INTEGER,"refresh_token" VARCHAR(254),"logininfo_id" BIGINT NOT NULL);
create table "openid_attributes" ("id" VARCHAR(254) NOT NULL,"key" VARCHAR(254) NOT NULL,"value" VARCHAR(254) NOT NULL);
create table "openid_infos" ("id" VARCHAR(254) NOT NULL,"logininfo_id" BIGINT NOT NULL);
create table "password_infos" ("hasher" VARCHAR(254) NOT NULL,"password" VARCHAR(254) NOT NULL,"salt" VARCHAR(254),"logininfo_id" BIGINT NOT NULL);
create table "posts" ("id" UUID NOT NULL PRIMARY KEY,"content" text NOT NULL,"group_id" UUID NOT NULL,"created_by" VARCHAR(254),"created_timestamp" BIGINT NOT NULL);
create index "idx_post_group" on "posts" ("group_id");
create table "subscriptions" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"user_id" UUID NOT NULL,"group_id" UUID NOT NULL);
create unique index "idx_subscr_comb" on "subscriptions" ("group_id","user_id");
create index "idx_subscr_group" on "subscriptions" ("group_id");
create index "idx_subscr_user" on "subscriptions" ("user_id");
create table "user_login_infos" ("user_id" VARCHAR(254) NOT NULL,"logininfo_id" BIGINT NOT NULL);
create table "users" ("userID" VARCHAR(254) NOT NULL PRIMARY KEY,"first_name" VARCHAR(254),"last_name" VARCHAR(254),"full_name" VARCHAR(254),"email" VARCHAR(254),"avatar_url" VARCHAR(254));

# --- !Downs

drop table "users";
drop table "user_login_infos";
drop table "subscriptions";
drop table "posts";
drop table "password_infos";
drop table "openid_infos";
drop table "openid_attributes";
drop table "oauth2_infos";
drop table "oauth1_infos";
drop table "login_infos";
drop table "groups";

