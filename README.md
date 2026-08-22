# Task Manager API

API RESTful em **Java 17 + Spring Boot 3** para gerenciamento de tarefas e categorias, com arquitetura em camadas, autenticação stateless via **JWT** e proteção contra acesso indevido a dados de outros usuários (IDOR).

## Tecnologias

- Java 17+
- Spring Boot 3 — Web, Security & JWT (JJWT), Data JPA, Validation
- PostgreSQL (ou MySQL / H2 em memória)
- Springdoc OpenAPI (Swagger UI)
- Lombok
- Maven

## Segurança

- **Autenticação stateless** via Bearer Token JWT
- **Proteção IDOR**: operações de CRUD vinculadas ao usuário autenticado no token — sem acesso a dados de terceiros
- **Propriedade cruzada**: tarefas só podem ser associadas a categorias do próprio usuário
- **Erros padronizados** via `@RestControllerAdvice` (400, 401, 403, 404)

## Como Executar

**Pré-requisitos**
- Docker: Docker + Docker Compose
- Manual: JDK 17+, Maven (ou `./mvnw`), PostgreSQL local

### Docker
```bash
docker compose up --build
```
> Credenciais e segredo JWT são definidos via variáveis de ambiente — não versionar valores reais.

### Manual
1. Clone o repositório:
   ```bash
   git clone https://github.com/nichesdev/task_manager.git
   cd task_manager
   ```
2. Configure `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/task_db
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   api.security.token.secret=sua_chave_secreta_aqui
   ```
3. Inicie a aplicação:
   ```bash
   ./mvnw spring-boot:run        # Linux/macOS
   mvnw.cmd spring-boot:run      # Windows
   ```

Aplicação: `http://localhost:8080` · Swagger UI: `http://localhost:8080/swagger-ui.html`

## Endpoints

> Rotas sob `/v1/tasks` e `/v1/categories` exigem `Authorization: Bearer <TOKEN_JWT>`.

### Autenticação

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/auth/register` | Cria usuário | Pública |
| `POST` | `/v1/auth/login` | Login e gera JWT | Pública |

```json
// register
{ "username": "...", "email": "...", "password": "..." }

// login
{ "email": "...", "password": "..." }
```

### Categorias

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/categories` | Cria categoria | Bearer JWT |

```json
{ "categoryName": "categoria teste" }
```

### Tarefas

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/tasks` | Cria tarefa | Bearer JWT |
| `GET` | `/v1/tasks/user/{userId}` | Lista por usuário | Bearer JWT |
| `GET` | `/v1/tasks/category/{categoryId}` | Lista por categoria | Bearer JWT |
| `GET` | `/v1/tasks/user/{userId}/priority?priority={PRIORIDADE}` | Lista por prioridade | Bearer JWT |
| `PUT` | `/v1/tasks/{id}` | Atualiza tarefa | Bearer JWT |
| `PATCH` | `/v1/tasks/{id}/status` | Atualiza status | Bearer JWT |
| `DELETE` | `/v1/tasks/{id}` | Remove tarefa | Bearer JWT |

```json
// criação (POST) / atualização (PUT)
{
    "title": "titulo task",
    "description": "descrição task",
    "priority": "ALTA",
    "status": "EM_ANDAMENTO",
    "dueDate": "2026-08-25",
    "categoryId": 1
}

// status (PATCH)
{ "status": "CONCLUIDA" }
```
---
