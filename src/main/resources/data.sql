--- Insert base questions here.
insert into base_question (id, position, category, description) values (
    1, 'Analista de Sistemas', 'Entrega', 'Cumpre os prazos determinados para suas atividades');
insert into base_question (id, position, category, description) values (
    2, 'Analista de Sistemas', 'Liderança', 'Se responsabiliza pelos resultados etc.');
insert into base_question (id, position, category, description) values (
    3, 'Coordenador', 'Zuera', 'Promove a zuera saudável e justificável.');
insert into base_question (id, position, category, description) values (
    4, 'Coordenador', 'Liderança', 'Garante que sua equipe está devidamente informada e acompanha as tretas.');

-- Insert the people that are going to be evaluated.
insert into employee (id, name, position) values (
    1, 'HeitorMatsui', 'Coordenador');
insert into employee (id, name, position) values (
    2, 'BrunoMuniz', 'Coordenador');
insert into employee (id, name, position) values (
    3, 'FelipeBanzai', 'Analista de Sistemas');
insert into employee (id, name, position) values (
    4, 'CiroBottini', 'Analista de Sistemas');

-- Insert users for the application
-- this should not be here... every employee is a user, right? Will tackle that later...
insert into user (id, username, password, enabled) values (
    1, 'BrunoMuniz', '123', true);
insert into user (id, username, password, enabled) values (
    2, 'HeitorMatsui', '123', true);

-- Finally, insert the mapping between user and employees to create a feedback for the user to fill
-- this here is the reason users and employees do not have auto-generated ids. For now.
insert into feedback (user_id, employee_id, state) values
    (1, 1, 'NOT_STARTED'), (1, 3, 'NOT_STARTED'), (1, 4, 'NOT_STARTED'), (2, 2, 'NOT_STARTED'),
    (2, 3, 'NOT_STARTED'), (2, 4, 'NOT_STARTED');
