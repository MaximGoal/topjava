DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES
       ('11.11.2001 11:00:00', 'first meal', 1000, 100000),
         ('11.11.2001 15:00:00', 'second meal', 1500, 100000),
         ('11.14.2001 10:00:00', 'first meal', 500, 100000),
         ('11.14.2001 14:00:00', 'second meal', 1000, 100000),
         ('11.19.2001 09:00:00', 'first meal', 1000, 100000),
         ('11.19.2001 14:00:00', 'second meal', 999, 100000)


