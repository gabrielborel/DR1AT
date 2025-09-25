# Exemplos de Requisições - DR1AT API

> Todos os endpoints (exceto login) exigem autenticação via JWT. Envie o token no header:
> `Authorization: Bearer <seu_token_jwt>`

---
URL da API hospedada no Render: 
`https://dr1-at-api.onrender.com`
---

## Login
**POST /api/auth/login**

**Corpo:**
```json
{
  "username": "professor",
  "password": "professor123"
}
```

**Resposta:**
```json
{
  "token": "<jwt_token>",
  "type": "Bearer",
  "username": "professor"
}
```

---

## Estudantes

### Criar estudante
**POST /api/students**
```json
{
  "name": "João Silva",
  "document": "123.456.789-01",
  "email": "joao@exemplo.com",
  "phone": "(11) 99999-9999",
  "address": "Rua das Flores, 123"
}
```

**Resposta:**
```json
{
  "id": 1,
  "name": "João Silva",
  "document": "123.456.789-01",
  "email": "joao@exemplo.com",
  "phone": "(11) 99999-9999",
  "address": "Rua das Flores, 123",
  "courses": []
}
```

### Listar estudantes
**GET /api/students**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "document": "123.456.789-01",
    "email": "joao@exemplo.com",
    "phone": "(11) 99999-9999",
    "address": "Rua das Flores, 123",
    "courses": [
      {
        "courseId": 10,
        "courseName": "Matemática",
        "courseCode": "MAT101",
        "grade": 8.5,
        "approved": true
      }
    ]
  }
]
```

### Matricular estudante em curso
**POST /api/students/{studentId}/courses/{courseId}**

Sem corpo.
**Resposta:**
200 OK (sem corpo)

### Deletar estudante
**DELETE /api/students/{id}**

Sem corpo.
**Resposta:**
204 No Content

---

## Cursos

### Criar curso
**POST /api/courses**
```json
{
  "name": "Matemática",
  "code": "MAT101"
}
```

**Resposta:**
```json
{
  "id": 10,
  "name": "Matemática",
  "code": "MAT101",
  "students": []
}
```

### Listar cursos
**GET /api/courses**
```json
[
  {
    "id": 10,
    "name": "Matemática",
    "code": "MAT101",
    "students": [
      {
        "studentId": 1,
        "studentName": "João Silva",
        "studentDocument": "123.456.789-01",
        "studentEmail": "joao@exemplo.com",
        "grade": 8.5,
        "approved": true
      }
    ]
  }
]
```

### Deletar curso
**DELETE /api/courses/{id}**

Sem corpo.
**Resposta:**
204 No Content

---

## Notas

### Atribuir nota
**POST /api/grades**
```json
{
  "studentId": 1,
  "courseId": 10,
  "grade": 8.5
}
```

**Resposta:**
```json
{
  "id": 1,
  "studentId": 1,
  "studentName": "João Silva",
  "courseId": 10,
  "courseName": "Matemática",
  "courseCode": "MAT101",
  "grade": 8.5,
  "approved": true
}
```

### Listar alunos aprovados em um curso
**GET /api/grades/course/{courseId}/approved**
```json
[
  {
    "id": 1,
    "studentId": 1,
    "studentName": "João Silva",
    "courseId": 10,
    "courseName": "Matemática",
    "courseCode": "MAT101",
    "grade": 8.5,
    "approved": true
  }
]
```

### Listar alunos reprovados em um curso
**GET /api/grades/course/{courseId}/failed**
```json
[
  {
    "id": 2,
    "studentId": 2,
    "studentName": "Maria Souza",
    "courseId": 10,
    "courseName": "Matemática",
    "courseCode": "MAT101",
    "grade": 5.0,
    "approved": false
  }
]
```

### Deletar nota
**DELETE /api/grades/{id}**

Sem corpo.
**Resposta:**
204 No Content
