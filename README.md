# Goomer Challenge - API
Primeiro trabalho prático (T1) da disciplina de Banco de Dados I: implementação do desafio proposto em [goomerdev/job-dev-backend-interview](https://github.com/goomerdev/job-dev-backend-interview) usando `Spring Boot` / Java


## Sobre a aplicação desenvolvida

### Foi pedido que fosse criada uma API capaz de:
- Listar todos os restaurantes
- Cadastrar novos restaurantes 
- Listar os dados de um restaurante
- Alterar os dados um restaurante 
- Excluir um restaurante 
- Listar todos os produtos de um restaurante 
- Criar um produto de um restaurante 
- Alterar um produto de um restaurante 
- Excluir um produto de um restaurante

### Diagrama lógico do banco de dados:
![db_diagram](https://user-images.githubusercontent.com/61244675/176101288-337b9b81-42ca-4160-87bf-59b8457f0e3b.png)


### Lista de endpoints:
- _GET_ `/restaurant`: lista todos os restaurantes;
- _GET_ `/restaurant/{id}`: lista o restaurante com o id especificado;
- _POST_ `/restaurant`: cria um novo restaurante;
- _PUT_ `/restaurant`: atualiza o restaurante com o id especificado;
- _DELETE_ `/restaurant/{id}`: deleta o restaurante com o id especificado;
- _GET_ `/restaurant/{id}/menu`: lista o menu (produtos) do restaurante com o id especificado;
- _GET_ `/product/{id}`: lista o produto com o id especificado;
- _POST_ `/product`: cria um novo produto;
- _PUT_ `/product`: atualiza o produto com o id especificado;
- _DELETE_ `/product/{id}`: deleta o produto com o id especificado.


## Configurando o ambiente
### _Pré-requisitos_
* [JDK 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [Docker](https://docs.docker.com/desktop/)
* [Postman](https://www.postman.com/downloads/) ou [Insominia](https://insomnia.rest/download)


### 1. Clonar o repositório e extrair:
```git clone https://github.com/UFES20221BDCOMP/goomer-challenge.git```

### 2. Gerar o container do banco de dados no Docker:
```docker-compose up -d``` 

### 3. Fazer build e rodar a aplicação:
```mvn clean install spring-boot:run```

### 4. Pré-cadastrar algumas categorias de produtos:
```docker exec -u postgres goomer_db psql goomerdb postgres -f ./scripts/categories.sql```


## Testando a API

### 1. Importe a [postman_collection.json](https://github.com/UFES20221BDCOMP/goomer-challenge/blob/develop/postman_collection.json) no `Postman` ou `Insomnia`:

![import_button](https://user-images.githubusercontent.com/61244675/176101217-8a3bc4cc-146c-4865-8332-b51ef14c2b77.png)

### 2. Execute qualquer requisição
![postman_view](https://user-images.githubusercontent.com/61244675/176101101-cc6aa6a2-807b-42eb-93a1-88c57c147c37.png)

#### 2.1. Para ver a documentação da API:
![postman_doc](https://user-images.githubusercontent.com/61244675/176101051-90296c30-0773-4ff2-ae8a-869bceacea34.png)






