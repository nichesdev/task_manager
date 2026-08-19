# 📋 Task Manager API

API REST para gerenciamento de tarefas e categorias, com autenticação stateless via JWT e persistência em banco de dados relacional.

---

## 🚀 Tecnologias Utilizadas

Java, Spring Boot (Spring Web, Spring Security, Spring Data JPA, Spring Validation), Hibernate, JSON Web Token (JJWT), PostgreSQL, Lombok e Apache Maven.

---

## 🔒 Recursos de Segurança

- Autenticação stateless via tokens JWT
- Criptografia de senhas com BCrypt
- Controle de permissões baseado em papéis (RBAC)

---

## 🚦 Endpoints

### Autenticação
- `POST /register` — Cadastro de usuário
- `POST /login` — Login e geração de token

### Tarefas
- `GET /tasks` — Listar tarefas
- `POST /tasks` — Criar tarefa
- `PUT /tasks/{id}` — Atualizar tarefa
- `DELETE /tasks/{id}` — Deletar tarefa

### Categorias
- `GET /categories` — Listar categorias
- `POST /categories` — Criar categoria
- `DELETE /categories/{id}` — Deletar categoria