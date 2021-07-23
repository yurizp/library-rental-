
## Library Rental  
  
 - [x] Teste Unitarios  
 - [x] Container  
 - [x] Sonar  
 - [x] CI/CD  
 - [x] Deploy (Não automatizado)  
  
## Good to know  
  
#### Sonar: 

O projeto executa o sonar a cada commit nas branchs do projeto o [link](https://sonarcloud.io/dashboard?id=yurizp_library-rental) do projeto.  
  
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=yurizp_library-rental&metric=coverage)](https://sonarcloud.io/dashboard?id=yurizp_library-rental)  
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=yurizp_library-rental&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=yurizp_library-rental)  
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=yurizp_library-rental&metric=bugs)](https://sonarcloud.io/dashboard?id=yurizp_library-rental)  
  
#### Deploy  
Para fazer deploy do projeto subi um cluster do Kubernetes na Digitalocean e criei os arquivos de depoy, eles estão dentro da pasta `deploy`. Neste momento o deploy acontece de forma manual.  
Para acessar a aplicação no cluster [http://206.189.252.88/swagger-ui.html#/](http://206.189.252.88/swagger-ui.html#/).  
  
### CI/CD CircleCi  
Sempre que é alterado o código do projeto o CircleCi executa uma rotina de executar os testes e gerar uma imagem do projeto no docker-hub.  
[Link docker hub](https://hub.docker.com/repository/docker/yurizp/library)  
  
## Como executar  
  
#### Docker  
* Você deve ter instalado em sua maquina o docker compose.  
* Caso você tenha um mysql server instalado em sua maquina você deve parar o serviço pois ele ira conflitar com as portas do container do mysq.  
  
Na raiz do projeto você deve executar ```docker-compose up```.  Assim que estiver de pé o projeto ele estara disponivel no endereço [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html#/)  
  
#### Outras formas  
* Caso queira executar o projeto por algum editor você deve ter um banco de dados Mysql para o qual apontar.  
* O projeto esta com as seguintes configurações padrões:  
> host: jdbc:mysql://127.0.0.1:3306/library12?createDatabaseIfNotExist=true <p>  
> user: root <p>  
> pass: password  
  
É possivel sobreescrever essas configurações alterando direto no arquivo `application.yaml` ou setando as variaveis de ambiente:  
  
> DB_NAME = O host do banco <p>  
> DB_USER = user do banco <p>  
> DB_PASS = senha do banco  
