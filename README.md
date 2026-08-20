# 📋 Task Manager API

API RESTful desenvolvida em **Java** e **Spring Boot 3** para gerenciamento de tarefas e categorias. A aplicação conta com autenticação stateless via **Spring Security & JWT**, arquitetura em camadas e proteção contra vulnerabilidades de controle de acesso (**IDOR**).

---

## 🛠️ Tecnologias e Ferramentas

- **Java 17+**
- **Spring Boot 3**
    - Spring Web
    - Spring Security & JWT (JJWT)
    - Spring Data JPA
    - Spring Validation
- **Banco de Dados:** PostgreSQL (ou MySQL / H2 em memória)
- **Documentação:** Springdoc OpenAPI (Swagger UI)
- **Utilitários:** Lombok
- **Gerenciador de Dependências:** Maven

---

## 🔒 Segurança e Regras de Negócio

- **Autenticação Stateless:** Rotas protegidas via Bearer Token JWT.
- **Proteção contra IDOR (*Insecure Direct Object Reference*):** Todas as operações de criação, leitura, atualização e exclusão são vinculadas estritamente ao identificador do usuário autenticado no token, impedindo acesso a dados de terceiros.
- **Validação de Propriedade Cruzada:** Tarefas só podem ser associadas a categorias pertencentes ao próprio usuário.
- **Tratamento Global de Erros:** Respostas padronizadas via `@RestControllerAdvice` para exceções como `400 Bad Request`, `401 Unauthorized`, `403 Forbidden` e `404 Not Found`.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 17 ou superior instalado
- Maven instalado (ou use o wrapper `./mvnw`)
- PostgreSQL rodando localmente (ou container Docker)

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/nichesdev/task_manager.git
   cd task_manager
   ```

2. **Configure o banco de dados:**
   Ajuste as propriedades no arquivo `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/task_db
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update

   # Segredo JWT
   api.security.token.secret=sua_chave_secreta_aqui
   ```

3. **Inicie a aplicação:**
   ```bash
   # Linux/macOS
   ./mvnw spring-boot:run

   # Windows
   mvnw.cmd spring-boot:run
   ```
   A API estará disponível em: `http://localhost:8080`

---

## 📖 Documentação Interativa (Swagger UI)

Com a aplicação rodando, acesse no navegador:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## 📌 Endpoints da API

> **Aviso:** Todos os endpoints sob `/v1/tasks` e `/v1/categories` necessitam do cabeçalho HTTP: `Authorization: Bearer <TOKEN_JWT>`.

### 🔐 Autenticação (User Controller)

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/auth/register` | Criação de um novo usuário | Pública |
| `POST` | `/v1/auth/login` | Login e geração de JWT | Pública |

#### Payload de Registro (`POST /v1/auth/register`):
```json
{
    "username": "Enzo Nascimento",
    "email": "enzonascimento@gmail.com",
    "password": "enzo1234"
}
```

#### Payload de Login (`POST /v1/auth/login`):
```json
{
    "email": "enzoniches@gmail.com",
    "password": "enzo1234"
}
```

---

### 📂 Categorias (Category Controller)

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/categories` | Cria uma categoria (valida usuário via JWT) | Bearer JWT |

#### Payload de Criação (`POST /v1/categories`):
```json
{
    "categoryName": "tem que pertencer ao user 1"
}
```

---

### 📝 Tarefas (Task Controller)

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/tasks` | Cria tarefa (valida IDOR de categoria via JWT) | Bearer JWT |
| `GET` | `/v1/tasks/user/{userId}` | Lista tarefas pelo ID do usuário | Bearer JWT |
| `GET` | `/v1/tasks/category/{categoryId}` | Lista tarefas pela categoria | Bearer JWT |
| `GET` | `/v1/tasks/user/{userId}/priority?priority={PRIORIDADE}` | Lista tarefas por prioridade | Bearer JWT |
| `PUT` | `/v1/tasks/{id}` | Altera informações da tarefa | Bearer JWT |
| `PATCH` | `/v1/tasks/{id}/status` | Altera status da tarefa | Bearer JWT |
| `DELETE` | `/v1/tasks/{id}` | Deleta tarefa | Bearer JWT |

#### Payload de Criação (`POST /v1/tasks`):
```json
{
    "title": "Testando PUT userid 2",
    "description": "Ajustes finais Backend",
    "priority": "ALTA",
    "status": "EM_ANDAMENTO",
    "dueDate": "2026-08-25",
    "categoryId": 1
}
```

#### Payload de Atualização Completa (`PUT /v1/tasks/{id}`):
```json
{
    "title": "ALTERAÇÃO?",
    "description": "NÃO VIRA IDOR HUHUHUHUHU",
    "priority": "MEDIA",
    "status": "EM_ANDAMENTO",
    "dueDate": "2026-08-25T12:00:00",
    "categoryId": 1
}
```

#### Payload de Atualização de Status (`PATCH /v1/tasks/{id}/status`):
```json
{
    "status": "CONCLUIDA"
}
```
---

## 👤 Autor

Desenvolvido por **[nichesdev](https://github.com/nichesdev)**.