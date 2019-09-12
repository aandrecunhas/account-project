# Account Project

API REST desenvolvida em Grails/Groovy que simula uma conta digital

## Como instalar (Docker)

* Acessar o diretório do projeto
* Executar o comando:```docker-compose up -d```
* Acessar a URL: http://localhost:8081/account-project/

## Como utilizar o sistema

O sistema possui cinco end-points:

1) (POST) account/create: Criar uma conta
2) (PATCH) account/update: Atualizar uma conta
3) (GET) account/limits: Visualiza todas as contas
4) (POST) transaction/createTransaction: cria uma transação do tipo Compra à Vista, Compra Parcelada ou Saque
5) (POST) transaction/createPayment: cria um pagamento (transação do tipo pagamento)

Para a utilização do sistema, por favor utilize o POSTMAN
* Link do projeto postman: https://documenter.getpostman.com/view/6549976/SVmsW1RX?version=latest

## Requisitos

## Diagrama de dados

## Testes Automatizados
* Testes de Unidade
 1) AccountSpec
 2) TransactionSpec
 
* Testes de Integração
 1) AccountServiceSpec
 2) TransactionServiceSpec
 
* Testes de Sistema
 1) Projeto Postman
