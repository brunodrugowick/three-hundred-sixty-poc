--- Insert base questions here.
insert into base_question (id, position, category, description) values (
	1, 'Analista de Sistemas', 'Entrega', 'A produtividade na realização das tarefas que estão sob sua responsabilidade, está de acordo com o esperado.');
insert into base_question (id, position, category, description) values (
	4, 'Analista de Sistemas', 'Relacionamento Interpessoal', 'Trata a todos com respeito, simpatia, presteza e educação, independentemente da hierarquia e do público, buscando um clima de harmonia, confiança e cooperação. ');
insert into base_question (id, position, category, description) values (
	5, 'Analista de Sistemas', 'Trabalho em Equipe', 'Tem sempre uma postura positiva frente às dificuldades dos colegas, ajudando-os a identificar riscos, oportunidades e soluções, sem comentários depreciativos ou ofensivos.');
insert into base_question (id, position, category, description) values (
	8, 'Analista de Sistemas', 'Comunicação Interpessoal', 'Em suas apresentações em público, sabe transmitir de forma clara e objetiva suas ideias, expectativas e opiniões.');
insert into base_question (id, position, category, description) values (
	11, 'Analista de Sistemas', 'Comprometimento', 'Se empenha para que os resultados do grupo sejam os melhores possíveis.');
insert into base_question (id, position, category, description) values (
	15, 'Analista de Sistemas', 'Atitude positiva e resiliência', 'Enfrenta problemas de maneira construtiva.');
insert into base_question (id, position, category, description) values (
	21, 'Analista de Sistemas', 'Liderança', 'Procura evoluir pessoal, profissional e intelectualmente, buscando aperfeiçoamento e atualização contínua de seus conhecimentos.');
insert into base_question (id, position, category, description) values (
	22, 'Analista de Sistemas', 'Maturidade/Foco', 'Diferencia as coisas importantes das não importantes');
insert into base_question (id, position, category, description) values (
	23, 'Coordenador', 'Entrega', 'Participa ativamente em novas oportunidades de negócio?');
insert into base_question (id, position, category, description) values (
	24, 'Coordenador', 'Entrega', 'Tem visibilidade e controle das prospecções relevantes, ponderando probabilidade de fechamento, custos de pré-vendas e relevância para sua área e empresa?');
insert into base_question (id, position, category, description) values (
	26, 'Coordenador', 'Entrega', 'Direciona a equipe para agregar maior valor e diferenciação nas propostas apresentadas para os clientes?');
insert into base_question (id, position, category, description) values (
	1, 'Coordenador', 'Entrega', 'A produtividade na realização das tarefas que estão sob sua responsabilidade, está de acordo com o esperado.');
insert into base_question (id, position, category, description) values (
	4, 'Coordenador', 'Relacionamento Interpessoal', 'Trata a todos com respeito, simpatia, presteza e educação, independentemente da hierarquia e do público, buscando um clima de harmonia, confiança e cooperação. ');
insert into base_question (id, position, category, description) values (
	5, 'Coordenador', 'Trabalho em Equipe', 'Tem sempre uma postura positiva frente às dificuldades dos colegas, ajudando-os a identificar riscos, oportunidades e soluções, sem comentários depreciativos ou ofensivos.');
insert into base_question (id, position, category, description) values (
	8, 'Coordenador', 'Comunicação Interpessoal', 'Em suas apresentações em público, sabe transmitir de forma clara e objetiva suas ideias, expectativas e opiniões.');
insert into base_question (id, position, category, description) values (
	11, 'Coordenador', 'Comprometimento', 'Se empenha para que os resultados do grupo sejam os melhores possíveis.');
insert into base_question (id, position, category, description) values (
	15, 'Coordenador', 'Atitude positiva e resiliência', 'Enfrenta problemas de maneira construtiva.');
insert into base_question (id, position, category, description) values (
	21, 'Coordenador', 'Liderança', 'Procura evoluir pessoal, profissional e intelectualmente, buscando aperfeiçoamento e atualização contínua de seus conhecimentos.');
insert into base_question (id, position, category, description) values (
	22, 'Coordenador', 'Maturidade/Foco', 'Diferencia as coisas importantes das não importantes');

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
