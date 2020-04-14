--- Insert base questions here.
insert into base_question (id, position, category, description) values (
	1, 'Analista de Sistemas', 'Levantamento de Requisitos', 'É capaz de entender seus usuários finais, mapeando suas necessidades em funcionalidades de forma satisfatória.');
insert into base_question (id, position, category, description) values (
	2, 'Coordenador', 'Comunicação', 'Se comunica com a frequência adequada com seus subordinados.');

-- Insert the people that are going to be evaluated.
insert into employee (id, name, position, email, password, roles, enabled) values
    (1, 'Heitor Marrakesh', 'Coordenador', 'heitor.marrakesh@email.com', '$2a$10$M7OAYIGqApcaVQ8P2Th2Wef.pt1wTYdHBRwT7Q9XE8V3BHo37bfZe', 'ROLE_USER,ROLE_ADMIN', true),
    (2, 'Bruno Mahoney', 'Coordenador', 'bruno.mahoney@email.com', '$2a$10$M7OAYIGqApcaVQ8P2Th2Wef.pt1wTYdHBRwT7Q9XE8V3BHo37bfZe', 'ROLE_USER,ROLE_ADMIN', true),
    (3, 'Enrique Iglesias', 'Analista de Sistemas', 'enrique.iglesias@email.com', '$2a$10$M7OAYIGqApcaVQ8P2Th2Wef.pt1wTYdHBRwT7Q9XE8V3BHo37bfZe', 'ROLE_USER,ROLE_ADMIN', true);

-- Finally, insert the mapping between user and employeesFeedbacks to create a feedback for the user to fill
-- this here is the reason users and employeesFeedbacks do not have auto-generated ids. For now.
insert into feedback (evaluator_id, relationship, evaluated_id, state) values
    (1, 'PEER', 2, 'NOT_STARTED'), (1, 'SUPERIOR', 3, 'NOT_STARTED'),
    (2, 'PEER', 1, 'NOT_STARTED'), (2, 'SUPERIOR', 3, 'NOT_STARTED'),
    (3, 'SUBORDINATE', 1, 'NOT_STARTED'), (3, 'SUBORDINATE', 2, 'NOT_STARTED');
