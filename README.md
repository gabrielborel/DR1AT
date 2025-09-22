# DR1AT - Sistema de Gerenciamento Acadêmico

## 📋 Pré-requisitos

- Java 17
- Docker e Docker Compose
- Maven

## 🚀 Como executar o projeto

### 1. Subir o banco PostgreSQL

```bash
# No diretório raiz do projeto
docker-compose up -d
```

### 2. Verificar se o banco está funcionando

```bash
# Ver logs dos containers
docker-compose logs postgres

# Verificar containers rodando
docker ps
```

### 3. Executar a aplicação

```bash
# Modo desenvolvimento
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Ou modo produção (padrão)
mvn spring-boot:run
```

## 🐘 Informações do Banco PostgreSQL

### Ambiente de Desenvolvimento (local)
- **Host:** localhost
- **Porta:** 5432
- **Database:** dr1at_db
- **Usuário:** dr1at
- **Senha:** dr1at

## 🔧 PgAdmin (Interface Web)

Acesse: http://localhost:5050

- **Email:** admin@dr1at.com
- **Senha:** admin123

### Configurar conexão no PgAdmin:
1. Host: postgres (nome do container)
2. Port: 5432
3. Database: dr1at_db
4. Username: dr1at
5. Password: dr1at

## 🔐 Autenticação

### Credenciais do Professor:
- **Username:** professor
- **Password:** professor123

### Endpoint de Login:
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "professor",
  "password": "professor123"
}
```

### Resposta do Login:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9mZXNzb3IiLCJpYXQiOjE2OTUxMjM0NTYsImV4cCI6MTY5NTIwOTg1Nn0.xyz",
  "username": "professor",
  "expiresIn": 86400
}
```

### ⚠️ **Importante - Usar JWT nas Requisições:**

Após fazer login, você receberá um **JWT token** que deve ser incluído no header `Authorization` de **todas as outras requisições**:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9mZXNzb3IiLCJpYXQiOjE2OTUxMjM0NTYsImV4cCI6MTY5NTIwOTg1Nn0.xyz
```

#### Exemplo de requisição autenticada:
```http
GET /api/students
Authorization: Bearer seu_jwt_token_aqui
Content-Type: application/json
```

**Observações:**
- 🔑 O token tem validade de **24 horas** (86400 segundos)
- 🚫 Sem o token, você receberá erro **403 Forbidden**
- ⏰ Após expirar, faça login novamente para obter um novo token

## 📚 Principais Endpoints

### Autenticação
- `POST /api/auth/login` - Login (público)

### Estudantes
- `GET /api/students` - Listar estudantes
- `POST /api/students` - Criar estudante
- `POST /api/students/{studentId}/courses/{courseId}` - Matricular estudante em curso
- `DELETE /api/students/{id}` - Deletar estudante

### Disciplinas
- `GET /api/courses` - Listar disciplinas
- `POST /api/courses` - Criar disciplina
- `DELETE /api/courses/{id}` - Deletar disciplina

### Notas
- `POST /api/grades` - Atribuir nota
- `GET /api/grades/course/{courseId}/approved` - Alunos aprovados (≥7.0)
- `GET /api/grades/course/{courseId}/failed` - Alunos reprovados (<7.0)
- `DELETE /api/grades/{id}` - Deletar nota

## 🚀 Deploy no Render

O projeto está configurado para deploy automático no Render. O Dockerfile utiliza multi-stage build:

1. **Build stage:** Compila o projeto com Maven
2. **Runtime stage:** Executa a aplicação com Java 17 JRE

### Variáveis de Ambiente para Produção:
- `SPRING_PROFILES_ACTIVE=prod`
- `PORT=8080` (configurado automaticamente pelo Render)

## ⚙️ Perfis de Execução

### Desenvolvimento (com logs detalhados)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
- Banco local PostgreSQL
- DDL: `create-drop` (recria tabelas a cada execução)
- Logs SQL habilitados
- Logs de debug habilitados

### Produção
```bash
mvn spring-boot:run
```
- Banco PostgreSQL no Render
- DDL: `update` (mantém dados existentes)
- Logs SQL desabilitados
- Logs apenas de nível INFO

## 🛠️ Comandos Docker Úteis

```bash
# Subir apenas o PostgreSQL
docker-compose up -d postgres

# Ver logs em tempo real
docker-compose logs -f postgres

# Parar todos os serviços
docker-compose down

# Remover volumes (limpar dados)
docker-compose down -v

# Restart dos serviços
docker-compose restart
```

## 📝 Notas Importantes

- ⚠️ **Atenção**: O perfil padrão é `prod`, que conecta no banco do Render
- Para desenvolvimento local, sempre use o perfil `dev`
- O banco de desenvolvimento recria as tabelas a cada execução
- O JWT tem expiração de 24 horas (86400 segundos)
