# Feedback Center - uma prova de conceito

Esse projeto tem o objetivo de validar algumas ideias com relação a como aplicar uma avaliação formal (Avaliação 360 Graus) de forma rápida, simples e flexível.

Veja as principais funcionalidades:
[![Quick Demo Video](README/yt_preview.png)](https://youtu.be/erlDfTm4c7A)

Você:

- define o conjunto de perguntas (quantas forem necessárias!) e a que cargos se aplicam;
- cria os funcionários definindo seus cargos;
- cria as avaliações que estabelecem sob que ponto de vista uma pessoa avalia a outra: `Subordinado`, `Par` ou `Superior`.
  - e pode ainda criar auto-avaliações.

O sistema: 

- coleta as respostas de acordo com a escala `Não atende`, `Atende parcialmente`, `Atende` e `Excede expectativas`, escala que evita o meio-termo, reforçando a necessidade de posicionamento claro de quem passa um feedback;
- exige que em todas as perguntas um exemplo seja dado, reforçando a importância de avaliações baseadas em situações reais;
- exige sugestões de melhoria para avaliações em `Não atende` ou `Atende parcialmente`, reforçando o comprometimento de cada pessoa com o crescimento da equipe.

# Manual do Administrador

Apenas 3 entidades precisam ser administradas.

1. Crie Perguntas, determinando no campo `Cargo` a que a cargo essas perguntas se aplicam. Você pode criar quantas perguntas quiser. 
    ```text
    Duas perguntas são disponibilizadas como exemplo. Crie suas próprias perguntas!
    ```
2. Crie funcionários que devem avaliar e ser avaliados. 
    ```text
    O campo `Cargo` será usado para determinar quais perguntas devem ser respondidas 
   por quem avaliar aquele funcionário.
    ```
3. Crie Avaliações para relacionar dois funcionários como Avaliador e Avaliado. É possível determinar como cada funcionário avalia outro através do campo `Relação`, cujos valores possíveis são:
    - Subordinado
    - Par
    - Superior
    - Auto (indica uma auto-avaliação)  
  
Fique atento pois nesta versão as relações e cargos não são validados, você deve garantir a corretude das informações.

É possível ver o estado de cada avaliação a qualquer momento. 

Também é possível acessar um relatório para cada avaliado, que sumariza todas as avaliações que ele recebeu agregando a pergunta e cargo do avaliador com uma média simples de todas as respostas.

# Funcões adicionais

## Reprocessar

É possível reprocessar uma avaliação, que significa recriá-la com base nas perguntas existentes.

Essa ação irá reiniciar uma avaliação e caso ela já possua respostas estas serão perdidas.

## Reprocessar Avaliações

A mesma coisa que Reprocessar, mas para todas as avaliações. Útil para iniciar um ciclo de avaliações após validação das informações.

# Segurança

Todas as *novas* senhas são `password`, mas há uma função para alteração de senha na aplicação.

Há 3 usuários inicialmente, com dados de exemplo para entender o funcionamento básico da aplicação:

- bruno.mahoney@email.com/password
- heitor.marrakesh@email.com/password
- enrique.iglesias@email.com/password

Faça o login e veja você mesmo.

# Seu próprio deploy

## Do jeito raíz

Se quer testar a aplicação mais a fundo em um ambiente próprio, quiçá executando um processo completo de Avaliação 360 Graus (quem sabe!), siga os seguintes passos:

1. Obtenha uma versão compilada do BETA na página de Releases;
2. Disponibilize as seguintes variáveis de ambiente (ou inicialize a aplicação com elas):
```
spring.datasource.url=jdbc:mysql://<hostname>:<port>/feedbackcenter?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=<mysql-user>
spring.datasource.password=<password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database=mysql
```
3. Rode o arquivo .jar com `java -jar <nome-do-arquivo>.jar`.

## Do jeito moderno

Em breve!
