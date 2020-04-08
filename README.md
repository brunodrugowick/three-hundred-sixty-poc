# Feedback Center - uma prova de conceito

Esse projeto tem o objetivo de validar algumas ideias com relação a como aplicar uma avaliação formal (Avaliação 360 Graus) de forma simplificada sem a necessidade de sistemas complexos ou caseiros (demais!).

# #LiveProgramming

Enquanto estiver programando nesse projeto provavelmente estarei compartilhando esse momento [aqui](https://hangouts.google.com/call/dYjm1LRwaYS-WCabJr39AEEE). 

# Segurança

Todas as senhas são `password` e não há como mudar isso no momento, mesmo para novos usuários.

Há 3 usuários inicialmente:

- bruno.mahoney@email.com/password
- heitor.marrakesh@email.com/password
- enrique.iglesias@email.com/password

# Como usar

Apenas 3 entidades devem ser administradas.

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

# Funcões adicionais

## Reprocessar

É possível reprocessar uma avaliação, que significa recriá-la com base nas perguntas existentes.

Essa ação irá reiniciar uma avaliação e caso ela já possua respostas estas serão perdidas.

## Reprocessar Avaliações

A mesma coisa que Reprocessar, mas para todas as avaliações. Útil para iniciar um ciclo de avaliações após validação das informações.
