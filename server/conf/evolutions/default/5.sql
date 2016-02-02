# --- !Ups
update groups set is_active = true;

# --- !Downs
update groups set is_active = false;