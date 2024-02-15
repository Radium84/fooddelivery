INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO auth (username, password) VALUES ('user', '$2a$10$SSG3kf9Dc63hwjO.WWo4neSqe7aWIMiQjDlaQGv.qagNAutewLHpS');
INSERT INTO auth (username, password) VALUES ('admin', '$2a$10$IkQC3d2pz/.rvj72/w5EleEKkKqzNv4Bx6Z0498D7qjhE25e6C0OC');

INSERT INTO auth_roles (auth_id, role_id) VALUES ((SELECT id FROM auth WHERE username='user'), (SELECT role_id FROM roles WHERE name='ROLE_USER'));
INSERT INTO auth_roles (auth_id, role_id) VALUES ((SELECT id FROM auth WHERE username='admin'), (SELECT role_id FROM roles WHERE name='ROLE_ADMIN'));