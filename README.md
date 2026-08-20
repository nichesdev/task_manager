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

> **Aviso:** Todos os endpoints sob `/v1/tasks` e `/v1/categories` necessitam do cabeçalho `Authorization: Bearer <TOKEN_JWT>`.

### 🔐 Autenticação

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/auth/register` | Cadastro de novo usuário | Pública |
| `POST` | `/v1/auth/login` | Autenticação e geração do token JWT | Pública |

```json
// POST /v1/auth/login
{
  "email": "dev@exemplo.com",
  "password": "senhaSegura123"
}
```

---

### 📂 Categorias

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/categories` | Cria uma categoria para o usuário | Bearer JWT |
| `GET` | `/v1/categories` | Lista todas as categorias do usuário | Bearer JWT |
| `GET` | `/v1/categories/{id}` | Detalhes de uma categoria por ID | Bearer JWT |
| `DELETE` | `/v1/categories/{id}` | Remove uma categoria | Bearer JWT |

```json
// POST /v1/categories
{
  "name": "Estudos"
}
```

---

### 📝 Tarefas

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/v1/tasks` | Cria uma nova tarefa associada ao usuário | Bearer JWT |
| `GET` | `/v1/tasks` | Lista todas as tarefas do usuário | Bearer JWT |
| `GET` | `/v1/tasks/{id}` | Busca tarefa por ID | Bearer JWT |
| `GET` | `/v1/tasks/category/{categoryId}` | Lista tarefas do usuário por categoria | Bearer JWT |
| `GET` | `/v1/tasks/status?status={STATUS}` | Lista tarefas filtradas por status | Bearer JWT |
| `GET` | `/v1/tasks/priority?priority={PRIORIDADE}` | Lista tarefas filtradas por prioridade | Bearer JWT |
| `PUT` | `/v1/tasks/{id}` | Atualização completa dos dados da tarefa | Bearer JWT |
| `PATCH` | `/v1/tasks/{id}/status` | Atualiza somente o status da tarefa | Bearer JWT |
| `DELETE` | `/v1/tasks/{id}` | Deleta uma tarefa | Bearer JWT |

#### Payload de Criação (`POST /v1/tasks`):
```json
{
  "title": "Refatorar camada de segurança",
  "description": "Remover vulnerabilidades IDOR e padronizar DTOs",
  "priority": "ALTA",
  "status": "EM_ANDAMENTO",
  "dueDate": "2026-08-30",
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

## 🧪 Testes Automatizados

Para executar os testes com **JUnit 5** e **Mockito**:

```bash
./mvnw test
```

---

## 👤 Autor

Desenvolvido por **[nichesdev](https://github.com/nichesdev)**.