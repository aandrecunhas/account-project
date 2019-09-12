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

**Requisitos Funcionais**

**RF1** - O sistema deve ser capaz de criar contas, as contas deve ter um limite de saque e um limite de crédito 

**RF2** - O sistema deve permitir a modificação do limite de saque e crédito de uma conta

**RF3** - O sistema deve exibir os limites de saque e crédito de uma conta

**RF4** - O sistema deve permitir a criação de uma transação de compra à vista, compra parcelada de uma conta considerando o limite de crédito da mesma, ou seja, o saldo devedor dessas transações total nunca deverá ser maior que o limite

**RF5** - O sistema deve permitir a criação de uma transação de saque considerando a somatória dos limites de saque e crédito, ou seja, o saldo devedor dessas transações nunca deve ser maior que a soma dos dois limites

**RF6** - Ao realizar um pagamento o sistema deve criar uma transação e abater o valor do saldo das transações de crédito (à vista e parcelado) e saque de acordo com o número de ordem e  da data de criação da transação.

**Requisitos Não Funcionais**

**RNF1** - O sistema deverá ser colocado em um container Docker

**RNF2** - O sistema deverá ter testes automatizados

**RNF3** - O sistema deverá ter endpoints disponíveis para controlar as suas funcionalidades

**RNF4** - Deverá ser utilizada a linguagem Groovy


## Diagrama de dados

## Testes Automatizados
* **Testes de Unidade**
 1) AccountSpec
 2) TransactionSpec
 
* **Testes de Integração**
 1) AccountServiceSpec
 2) TransactionServiceSpec
 
* **Testes de Sistema**
 1) Projeto Postman
