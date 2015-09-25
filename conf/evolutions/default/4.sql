# --- !Ups
alter table groups add is_active boolean;

# --- !Downs
alter table groups drop is_active;