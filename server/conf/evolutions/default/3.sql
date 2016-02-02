# --- !Ups
update posts set is_active = true;

# --- !Downs
update posts set is_active = false;