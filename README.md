# Radar-Saude-API

É um projeto spingboot/Maven, utilizando as seguintes dependências:

* spring data jpa

* spring doc open api

* lombok

* modelmapper

* flywaydb

 

Para conexão do banco de dados basta mudar as configurações abaixo no application.properties do projeto conforme necessário.

 

spring.datasource.url=jdbc:postgresql://localhost:5432/radarsaude
spring.datasource.username=postgres
spring.datasource.password=root

 

Basta criar o data base, login e senha o resto o flyway faz pra gente.

Projeto rodando na porta padrão 8080.
 
API 100% documentada com open api + Swagger em “/api-doc.html”.

Todos os endpoints cobertos com test case em src/test/java.

Configurações do hibernate pra criação de tabelas e mostrar sql desativadas.

O anti pattern do spring.jpa.open-in-view esta setado com false.


A classe PersonControllerTest tem um método populateDataForTest() que popula 
o banco com 100 registro toda vez que é executado. 
Caso isto seja um problema basta comentar sua chamada no método:

@BeforeAll
    void setUp()

da referida classe.
