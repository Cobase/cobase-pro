# --- !Ups
alter table posts add is_active boolean;

# --- !Downs
alter table posts drop is_active;