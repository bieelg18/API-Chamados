# API de Chamados

API REST desenvolvida em Java com Spring Boot para gerenciamento de chamados de suporte.

O projeto permite o cadastro e autenticação de usuários, abertura e acompanhamento de chamados e gerenciamento dos atendimentos por usuários com nível de suporte.

A aplicação utiliza Spring Security com autenticação via JWT e controle de acesso baseado em permissões.

## Funcionalidades

### Usuários

* Cadastro de novos usuários
* Autenticação através de e-mail e senha
* Geração de token JWT após autenticação
* Consulta de usuários cadastrados
* Busca de usuário por e-mail
* Atualização dos próprios dados cadastrais
* Alteração de nível de permissão
* Exclusão de usuários

### Chamados

* Abertura de novos chamados
* Listagem dos chamados pertencentes ao usuário autenticado
* Listagem geral de chamados para usuários de suporte
* Filtro de chamados por status
* Alteração de chamado para "Em Atendimento"
* Encerramento de chamados
* Classificação de chamados como improcedentes
* Exclusão de chamados
* Registro automático das datas de abertura e fechamento

## Autenticação e autorização

A API utiliza Spring Security e JWT para autenticação.

Após realizar o login através do endpoint:

```http
POST /auth/login
```

a API retorna um token JWT.

Nas rotas protegidas, o token deve ser enviado através do header:

```http
Authorization: Bearer <token>
```

A aplicação possui dois níveis de permissão:

### PADRAO

Usuários padrão podem:

* Alterar os próprios dados cadastrais
* Abrir chamados
* Visualizar os próprios chamados

### SUPORTE

Usuários de suporte possuem acesso às funcionalidades administrativas, incluindo:

* Listar usuários
* Buscar usuários por e-mail
* Alterar permissões
* Excluir usuários
* Visualizar todos os chamados
* Filtrar chamados por status
* Alterar o status dos chamados
* Encerrar chamados
* Classificar chamados como improcedentes
* Excluir chamados

As senhas dos usuários são armazenadas utilizando BCrypt.

## Status dos chamados

Os chamados podem assumir diferentes estados durante seu ciclo de atendimento:

```text
ABERTO
EM_ATENDIMENTO
FECHADO
IMPROCEDENTE
```

A API possui regras de negócio que controlam as transições entre esses estados e impedem operações incompatíveis com o status atual do chamado.

## Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* Spring Security
* JWT (JJWT)
* PostgreSQL
* Flyway
* Hibernate
* MapStruct
* Lombok
* Swagger / OpenAPI
* Maven

## Arquitetura

O projeto foi organizado separando as principais responsabilidades da aplicação:

```text
auth
├── autenticação e login

chamado
├── controllers
├── DTOs
├── mappers
├── services
├── repositories
└── entidades relacionadas aos chamados

usuario
├── controllers
├── DTOs
├── mappers
├── services
├── repositories
└── entidades relacionadas aos usuários

security
├── configuração do Spring Security
├── autenticação JWT
├── filtros de segurança
└── tratamento de acesso não autorizado

exception
└── exceptions e tratamento global de erros
```

## Banco de dados

A aplicação utiliza PostgreSQL como banco de dados relacional e Flyway para controle das migrations.

Configure a conexão no `application.properties` de acordo com seu ambiente.

Também é necessário definir uma chave secreta para geração e validação dos tokens JWT.

Exemplo:

```properties
jwt.secret=${JWT_SECRET}
```

A variável `JWT_SECRET` deve ser configurada no ambiente de execução e não deve ser adicionada diretamente ao repositório.

## Documentação da API

A documentação dos endpoints foi criada utilizando Swagger/OpenAPI.

Com a aplicação em execução, a interface do Swagger pode ser acessada através de:

```text
http://localhost:8080/swagger-ui/index.html
```

As rotas protegidas podem ser testadas através do Swagger utilizando o token JWT obtido no endpoint de login.

## Executando o projeto

### Pré-requisitos

* Java 17
* PostgreSQL
* Maven ou Maven Wrapper
* Banco de dados configurado
* Variável de ambiente `JWT_SECRET`

Clone o repositório:

```bash
git clone https://github.com/bieelg18/API-Chamados.git
```

Entre na pasta:

```bash
cd API-Chamados
```

Configure o PostgreSQL e a variável de ambiente utilizada pelo JWT.

Execute utilizando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A API ficará disponível por padrão em:

```text
http://localhost:8080
```

## Objetivo do projeto

O projeto foi desenvolvido com o objetivo de praticar o desenvolvimento de APIs REST utilizando Java e Spring Boot, aplicando conceitos como:

* Arquitetura em camadas
* DTOs e mapeamento de objetos
* Relacionamentos com JPA/Hibernate
* Regras de negócio
* Tratamento global de exceções
* Autenticação e autorização
* Spring Security
* JWT
* Controle de acesso baseado em roles
* Persistência com PostgreSQL
* Versionamento do banco de dados com Flyway
* Documentação de APIs com Swagger/OpenAPI
