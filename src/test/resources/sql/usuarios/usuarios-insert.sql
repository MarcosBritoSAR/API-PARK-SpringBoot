--O Spting Security epsera que no banco de dados, tenham epenas senha criptografadas.
insert into USUARIOS (id, username, password, role) values (100, 'ana@email.com', '$2a$12$hDkI17iHk4.dcQxZI7KdkOXP2ggmoHpUraSUg3jcacE9GxhXfpdPO', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101, 'bia@email.com', '$2a$12$hDkI17iHk4.dcQxZI7KdkOXP2ggmoHpUraSUg3jcacE9GxhXfpdPO', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102, 'bob@email.com', '$2a$12$hDkI17iHk4.dcQxZI7KdkOXP2ggmoHpUraSUg3jcacE9GxhXfpdPO', 'ROLE_CLIENTE');