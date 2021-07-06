INSERT INTO USER(name, email, password) VALUES ('claudio', 'aclaudiojj@gmail.com', '$2a$10$w1zHKnshd85cAAx8STh7.OLFNKIJSd2nHEbdHJB80usWWpuoJ35hW');
INSERT INTO USER(name, email, password) VALUES ('moderator', 'claudinhotkd@gmail.com', '$2a$10$w1zHKnshd85cAAx8STh7.OLFNKIJSd2nHEbdHJB80usWWpuoJ35hW');

insert into PROFILE(id, name) values (1, 'ROLE_STUDENT');
insert into PROFILE(id, name) values (2, 'ROLE_MODERATOR');

insert into USER_PROFILES(user_id, profiles_id) VALUES (1, 1);
insert into USER_PROFILES(user_id, profiles_id) VALUES (2, 2);

insert into COURSE(name, category) values ('spring boot', 'programming backend');
insert into COURSE(name, category) values ('php', 'programming backend');
insert into COURSE(name, category) values ('react', 'programming frontend');

insert into TOPIC(title, message, created_at, status, author_id, course_id) values ('duvida 1', 'error while creating project', '2021-07-01 12:00:00', 'NOT_ANSWERED', 1, 1);
insert into TOPIC(title, message, created_at, status, author_id, course_id) values ('duvida 1', 'error while creating project', '2021-07-01 12:00:00', 'NOT_ANSWERED', 1, 1);
insert into TOPIC(title, message, created_at, status, author_id, course_id) values ('duvida 1', 'error while creating project', '2021-07-01 12:00:00', 'NOT_ANSWERED', 1, 2);