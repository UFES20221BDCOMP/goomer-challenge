<img src="https://user-images.githubusercontent.com/61244675/176179939-0a0ccfff-1a99-4005-8028-5a0a028e3de9.png" alt="goomer-logo" width="200"/>

# Goomer Challenge
Primeiro trabalho prático (T1) da disciplina de Banco de Dados I: implementação do desafio proposto em [goomerdev/job-dev-backend-interview](https://github.com/goomerdev/job-dev-backend-interview) usando `Spring Boot` / Java.



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
<img src="https://user-images.githubusercontent.com/61244675/176101288-337b9b81-42ca-4160-87bf-59b8457f0e3b.png" alt="db_diagram"/>


### Lista de endpoints:
- **_GET_** `/restaurant`: lista todos os restaurantes;
- **_GET_** `/restaurant/{id}`: lista o restaurante com o id especificado;
- **_POST_** `/restaurant`: cria um novo restaurante;
- **_PUT_** `/restaurant`: atualiza o restaurante com o id especificado;
- **_DELETE_** `/restaurant/{id}`: deleta o restaurante com o id especificado;
- **_GET_** `/restaurant/{id}/menu`: lista o menu (produtos) do restaurante com o id especificado;
- **_GET_** `/product/{id}`: lista o produto com o id especificado;
- **_POST_** `/product`: cria um novo produto;
- **_PUT_** `/product`: atualiza o produto com o id especificado;
- **_DELETE_** `/product/{id}`: deleta o produto com o id especificado.



## Configurando o ambiente
### _Pré-requisitos:_
* [JDK 11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [Docker](https://docs.docker.com/desktop/)
* [Postman](https://www.postman.com/downloads/) ou [Insominia](https://insomnia.rest/download)


### 1. Clonar o repositório:
  ```git clone https://github.com/UFES20221BDCOMP/goomer-challenge.git```

### 2. A partir da pasta base do projeto, executar os seguintes comandos:

#### 2.1. Para gerar o container do banco de dados no Docker: 
  ```docker-compose up -d``` 

#### 2.2. Para fazer o build e rodar a aplicação:
  ```mvn clean install spring-boot:run```

#### 2.3. Para pré-cadastrar algumas categorias de produtos:
  ```docker exec -u postgres goomer_db psql goomerdb postgres -f ./scripts/categories.sql```



## Testando a API

### 1. Importe o arquivo [postman_collection.json](https://github.com/UFES20221BDCOMP/goomer-challenge/blob/master/postman_collection.json) no `Postman` ou `Insomnia`:

  <img src="https://user-images.githubusercontent.com/61244675/176101217-8a3bc4cc-146c-4865-8332-b51ef14c2b77.png" alt="import_button" width="400"/>

### 2. Execute qualquer requisição
  <img src="https://user-images.githubusercontent.com/61244675/176101101-cc6aa6a2-807b-42eb-93a1-88c57c147c37.png" alt="postman_view" width="900"/>

#### 2.1. Para ver a documentação da API: 
  <img src="https://user-images.githubusercontent.com/61244675/176101051-90296c30-0773-4ff2-ae8a-869bceacea34.png" alt="postman_doc" width="400"/>






