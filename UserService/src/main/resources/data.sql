
INSERT INTO users.roles (id, name)
VALUES ('1', 'ADMIN');

INSERT INTO users.roles (id, name)
VALUES ('2', 'USER');

INSERT INTO users.users (uuid, create_date, mail, nick, password, status, update_date)
VALUES ('76601d1d-eded-474e-8d3f-bdff9ea0c177', '1111-11-11 11:11:11.000', 'ADMIN@gmail.com', 'BOSS',
        '$2a$10$NV5Uk.Xnl4WFDBfce04cYuLCldGuf5VubtgOcjfXD3bmjhlGPgH56', 'ACTIVATED', '1111-11-11 11:11:11.000');

INSERT INTO users.users (uuid, create_date, mail, nick, password, status, update_date)
VALUES ('84b25364-d220-42ae-b971-f1a4952aeef3', '1000-01-01 00:00:00.000', 'USER@gmail.com', 'USER',
        '$2a$10$NV5Uk.Xnl4WFDBfce04cYuLCldGuf5VubtgOcjfXD3bmjhlGPgH56', 'ACTIVATED', '1000-01-01 00:00:00.000');

INSERT INTO users.user_roles (user_uuid, role_id) VALUES ('76601d1d-eded-474e-8d3f-bdff9ea0c177', '1');
INSERT INTO users.user_roles (user_uuid, role_id) VALUES ('84b25364-d220-42ae-b971-f1a4952aeef3', '2');




