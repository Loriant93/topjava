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

INSERT INTO meals(date_time, description, calories, user_id)
VALUES (TIMESTAMP '2020-10-18 10:23:54','breakfast',200,100000),
       (TIMESTAMP '2020-10-19 15:23:54','lunch',1000,100000),
       (TIMESTAMP '2020-10-19 20:23:54','dinner',900,100001);