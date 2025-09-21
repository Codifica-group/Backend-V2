# Backend-V2

Versão Clean Arch da aplicação Eleve.

## Escopo

Este repositório contém o código-fonte do backend da aplicação Eleve, um sistema de gerenciamento para um pet shop. A aplicação lida com o gerenciamento de clientes, pets, agendamentos de serviços, produtos, despesas e usuários.

## Arquitetura

O projeto é estruturado baseado em conceitos de Clean Arch e SOLID, separando as classes em diferentes camadas para facilitar a manutenção e escalabilidade.

| Pasta | Descrição                                                                                                                                       |
| --- |-------------------------------------------------------------------------------------------------------------------------------------------------|
| `src/main/java/codifica/eleve` | Raiz do código-fonte da aplicação.                                                                                                              |
| `.../config` | Contém as configurações da aplicação, incluindo segurança, tratamento de exceções e inicialização de dados em ambiente de desenvolvimento.      |
| `.../core/application` | Camada de aplicação, contendo os casos de uso e as portas de entrada/saída.                                                                     |
| `.../core/domain` | Camada de domínio, que contém as entidades de negócio e a lógica de domínio.                                                                    |
| `.../infrastructure` | Camada de infraestrutura, que contém as implementações das portas de saída, como adaptadores para bancos de dados, serviços externos e eventos. |
| `.../interfaces` | Camada de interface, responsável por expor a aplicação para o mundo externo, como controladores REST e DTOs.                                    |
| `src/main/resources` | Contém os arquivos de configuração da aplicação, como `application.properties`.                                                                                                    |

## Eventos

A aplicação utiliza RabbitMQ para a comunicação assíncrona entre os serviços. Os seguintes eventos são utilizados:

* **`cliente.para-cadastrar`**: Evento disparado para solicitar o cadastro de um novo cliente.
* **`pet.para-cadastrar`**: Evento disparado para solicitar o cadastro de um novo pet.

## Camadas de Segurança

A aplicação implementa as seguintes camadas de segurança:

* **Autenticação via JWT (JSON Web Token)**: O acesso aos endpoints protegidos requer um token JWT válido no cabeçalho `Authorization`.
* **Bloqueio de IP por Tentativas de Login**: Após um número máximo de tentativas de login falhas, o endereço de IP do cliente é temporariamente bloqueado para evitar ataques de força bruta.
* **CORS (Cross-Origin Resource Sharing)**: A aplicação é configurada para aceitar requisições de origens permitidas, definidas na variável de ambiente `CORS_ALLOWED_ORIGINS`.
* **Validação de Strings**: Utiliza a anotação `@SafeString` para validar as entradas de texto e prevenir ataques de XSS (Cross-Site Scripting) e SQL Injection.

## Compilação e Execução

### Pré-requisitos

* Java 21
* Maven 3.9 ou superior
* Docker e Docker Compose
* Variáveis de ambiente configuradas

### Variáveis de Ambiente

As seguintes variáveis de ambiente precisam ser configuradas:

| Variável                  | Descrição                                                                                   |
| ------------------------- |---------------------------------------------------------------------------------------------|
| `SPRING_PROFILES_ACTIVE`  | Ativa o perfil de configuração do Spring. **Padrão:** `dev` ou `prod`.                      |
| `DB_URL`                  | Endereço de conexão do banco de dados. **Padrão dev:** `jdbc:mysql://localhost:3306/Eleve`. |
| `DB_USERNAME`             | Nome de usuário do banco de dados. **Padrão dev:** `root`.                                  |
| `DB_PASSWORD`             | Senha do banco de dados. **Deve ser definida.**                                             |
| `RABBITMQ_USERNAME`       | Nome de usuário para o serviço de mensageria RabbitMQ. **Padrão dev:** `myuser`.            |
| `RABBITMQ_PASSWORD`       | Senha para o serviço de mensageria RabbitMQ. **Padrão dev:** `secret`.                      |
| `SECRET_KEY`              | Chave secreta para assinar tokens de autenticação (JWT). **Deve ser definida.**             |
| `TOKEN_EXPIRATION_TIME`   | Tempo de validade do token em milissegundos. **Padrão dev:** `28800000` (8 horas).          |
| `CORS_ALLOWED_ORIGINS`    | Libera o acesso à API para o frontend. **Padrão dev:** `http://localhost:5173`.             |
| `ORS_API_KEY`             | Chave da API do OpenRouteService para cálculos de rota. **Deve ser definida.**              |

### Passos para Execução

1.  **Após configurar as variáveis de ambiente, inicie o Docker Compose:**

    ```bash
    docker-compose up -d
    ```

2.  **Compile e execute a aplicação através da sua IDE, ou com Maven:**

    ```bash
    ./mvnw spring-boot:run
    ```