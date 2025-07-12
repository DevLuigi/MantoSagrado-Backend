
<div align="center">
    <h1> Manto Sagrado - Ecommerce </h1>
</div>


<div align="center">
   <img src="http://img.shields.io/static/v1?label=STATUS&message=FINALIZADO&color=RED&style=for-the-badge" alt="badge-finalizado"/>
</div>

<br>

### Tópicos 

- [Descrição do projeto](#descrição-do-projeto)

- [Funcionalidades](#funcionalidades)

- [Ferramentas utilizadas](#ferramentas-utilizadas)

- [Acesso ao projeto](#acesso-ao-projeto)

- [Como rodar o projeto](#como-rodar-o-projeto)

- [Desenvolvedores](#desenvolvedores)


<br>

## Descrição do projeto 

<p align="justify">
 O Manto Sagrado é um projeto acadêmico desenvolvido como parte das disciplinas de Desenvolvimento de sistemas web e Projeto integrador: Desenvolvimento de sistemas orientados a web. Nosso objetivo é construir um e-commerce funcional para a venda de camisas de futebol, aplicando conceitos aprendidos sobre desenvolvimento web fullstack.

Este projeto nos permite explorar tecnologias modernas para criação de aplicações web, incluindo autenticação de usuários, manipulação de banco de dados e integração com APIs externas.
<br>

## Funcionalidades

:heavy_check_mark: `Funcionalidade 1:` CRUD de produtos.

:heavy_check_mark: `Funcionalidade 2:` CRUD de usuários Administradores/Estoquistas.

:heavy_check_mark: `Funcionalidade 3:` Controle de estoque de produtos.

:heavy_check_mark: `Funcionalidade 4:` Manutenção das vendas dos clientes.

:heavy_check_mark: `Funcionalidade 5:` Login de Administradores/Estoquistas.

:heavy_check_mark: `Funcionalidade 6:` CRUD de clientes (Cadastro do endereço utilizando API externa).

:heavy_check_mark: `Funcionalidade 7:` Login de clientes.

:heavy_check_mark: `Funcionalidade 8:` Carrinho de compras.

:heavy_check_mark: `Funcionalidade 9:` Calculo do Frete (Utilizando API externa).

:heavy_check_mark: `Funcionalidade 10:` Checkout do carrinho de compras.

:heavy_check_mark: `Funcionalidade 11:` Lista de pedidos realizados dos clientes e resumo deles.

<br>

## Ferramentas utilizadas
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)](#)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=fff)](#)
[![Git](https://img.shields.io/badge/Git-F05032?logo=git&logoColor=fff)](#)
###

<br>

## Acesso ao projeto

Você pode [acessar o código fonte do projeto front-end](https://github.com/DevLuigi/MantoSagrado-Frontend).

<br>

## Como rodar o projeto

Para executar este projeto localmente, siga os passos abaixo:

1 - Clone o repositório:
~~~bash
  git clone https://github.com//DevLuigi/MantoSagrado-Backend.git
~~~

2 - Acesse o diretório do projeto:
~~~bash
  cd substitua/pelo/diretorio/onde/foi/clonado/MantoSagrado-Backend
~~~

3 - Configure as variáveis de ambiente:
Altere o arquivo application.properties no diretório src/main/resources, e defina as configurações do banco de dados e demais variáveis exigidas pelo projeto. Exemplo básico:
~~~bash
  spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
  spring.datasource.username=seu_usuario
  spring.datasource.password=sua_senha
  spring.jpa.hibernate.ddl-auto=update
~~~

4 - Compile o projeto com Maven:
~~~bash
  ./mvnw clean install
~~~

5 - Execute a aplicação:
~~~bash
  ./mvnw spring-boot:run
~~~

6 - Acesse a API:
A aplicação será executada em http://localhost:8080 (por padrão). Você pode testar os endpoints utilizando ferramentas como Postman, Insomnia ou via cURL.

<br>

## Desenvolvedores

| [<img src="https://avatars.githubusercontent.com/u/159407896?v=4" width=115><br><sub>André Santos da Silva</sub>](https://github.com/ngxdre) | [<img src="https://avatars.githubusercontent.com/u/159090497?v=4" width=115><br><sub>Breno carneiro bosan camilo</sub>](https://github.com/Brenuu)  |  [<img src="https://avatars.githubusercontent.com/u/142193648?v=4" width=115><br><sub>Eduardo Matos</sub>](https://github.com/eduardomts1)  | [<img src="https://avatars.githubusercontent.com/u/89977964?s=400&u=a0d21d2cf86edf9e2f66bcef496882e445f38f6d&v=4" width=115><br><sub>Luigi da Silva Coelho</sub>](https://github.com/DevLuigi) |
| :---: | :---: | :---: | :---: 

