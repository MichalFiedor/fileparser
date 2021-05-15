INSERT INTO authorities (name)
values ('ROLE_USER');

INSERT INTO users (username, password, enabled)
  values ('user',
    '$2a$10$fYc7qW1ZbSKfiOH2BX8u0ex93zq2EBbxOYNXLdKFquMk81IAlIWZ2',
    1);


INSERT INTO user_authority (user_id, authorities_id)
values (1,1);