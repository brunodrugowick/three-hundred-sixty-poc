-- Inserts demo base questions.
insert into base_question (id, position, category, description) values (
	1, 'Analista de Sistemas', 'Levantamento de Requisitos', 'É capaz de entender seus usuários finais, mapeando suas necessidades em funcionalidades de forma satisfatória.');
insert into base_question (id, position, category, description) values (
	2, 'Coordenador', 'Comunicação', 'Se comunica com a frequência adequada com seus subordinados.');

-- Inserts demonstration people to be evaluated.
insert into employee (id, name, position, email, password, roles, enabled) values
    (1, 'Heitor Marrakesh', 'Coordenador', 'heitor.marrakesh@email.com', '$2a$10$M7OAYIGqApcaVQ8P2Th2Wef.pt1wTYdHBRwT7Q9XE8V3BHo37bfZe', 'ROLE_USER,ROLE_ADMIN', true),
    (2, 'Bruno Mahoney', 'Coordenador', 'bruno.mahoney@email.com', '$2a$10$M7OAYIGqApcaVQ8P2Th2Wef.pt1wTYdHBRwT7Q9XE8V3BHo37bfZe', 'ROLE_USER,ROLE_ADMIN', true),
    (3, 'Enrique Iglesias', 'Analista de Sistemas', 'enrique.iglesias@email.com', '$2a$10$M7OAYIGqApcaVQ8P2Th2Wef.pt1wTYdHBRwT7Q9XE8V3BHo37bfZe', 'ROLE_USER,ROLE_ADMIN', true);

-- Finally, insert the demonstration mapping between user and employees to create a feedback.
insert into feedback (evaluator_id, relationship, evaluated_id, state) values
    (1, 'PEER', 2, 'NOT_STARTED'), (1, 'SUPERIOR', 3, 'NOT_STARTED'),
    (2, 'PEER', 1, 'NOT_STARTED'), (2, 'SUPERIOR', 3, 'NOT_STARTED'),
    (3, 'SUBORDINATE', 1, 'NOT_STARTED'), (3, 'SUBORDINATE', 2, 'NOT_STARTED');

-- Generate questions. This process is automatic for feedbacks added on the application
INSERT INTO question (id, category, description, evaluation, example, improvement, title, evaluated_id, evaluator_id)
    VALUES
           (1, 'Coordenador', 'Se comunica com a frequência adequada com seus subordinados.', NULL, NULL, NULL, 'Comunicação', 1, 2),
           (2, 'Coordenador', 'Se comunica com a frequência adequada com seus subordinados.', NULL, NULL, NULL, 'Comunicação', 1, 3),
           (3, 'Coordenador', 'Se comunica com a frequência adequada com seus subordinados.', NULL, NULL, NULL, 'Comunicação', 2, 1),
           (4, 'Coordenador', 'Se comunica com a frequência adequada com seus subordinados.', NULL, NULL, NULL, 'Comunicação', 2, 3),
           (5, 'Analista de Sistemas', 'É capaz de entender seus usuários finais, mapeando suas necessidades em funcionalidades de forma satisfatória.', NULL, NULL, NULL, 'Levantamento de Requisitos', 3, 1),
           (6, 'Analista de Sistemas', 'É capaz de entender seus usuários finais, mapeando suas necessidades em funcionalidades de forma satisfatória.', NULL, NULL, NULL, 'Levantamento de Requisitos', 3, 2);
