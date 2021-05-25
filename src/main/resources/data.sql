INSERT INTO authorities (name)
values ('ROLE_USER');

INSERT INTO users (username, password, enabled)
  values ('user',
    '$2y$10$dGlkyfKzipeFW9b6x35jGeavb7yfAPB774fJgJE.2c97uzpA1mnk2',
    1);


INSERT INTO user_authority (user_id, authorities_id)
values (1,1);