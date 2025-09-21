# DR1AT - Sistema de Gerenciamento Acadêmico

## 📋 Pré-requisitos

- Java 21
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

# Ou modo normal
mvn spring-boot:run
```

## 🐘 Informações do Banco PostgreSQL

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

## 📚 Principais Endpoints

### Autenticação
- `POST /api/auth/login` - Login (público)

### Estudantes
- `GET /api/students` - Listar estudantes
- `POST /api/students` - Criar estudante
- `GET /api/students/{id}` - Buscar por ID
- `PUT /api/students/{id}` - Atualizar estudante
- `DELETE /api/students/{id}` - Deletar estudante

### Disciplinas
- `GET /api/courses` - Listar disciplinas
- `POST /api/courses` - Criar disciplina
- `GET /api/courses/{id}` - Buscar por ID
- `PUT /api/courses/{id}` - Atualizar disciplina
- `DELETE /api/courses/{id}` - Deletar disciplina

### Notas
- `GET /api/grades` - Listar notas
- `POST /api/grades` - Atribuir nota
- `GET /api/grades/course/{id}/approved` - Alunos aprovados (≥7.0)
- `GET /api/grades/course/{id}/failed` - Alunos reprovados (<7.0)

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

## ⚙️ Perfis de Execução

### Desenvolvimento (com logs detalhados)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Produção
```bash
mvn spring-boot:run
```
