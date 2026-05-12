# Sistema de Gerenciamento de Clínica Médica

## Descrição do Sistema

API REST para gerenciamento de uma clínica médica, permitindo o cadastro de médicos e especialidades. Um médico pode ter várias especialidades e uma especialidade pode pertencer a vários médicos (relacionamento Muitos-para-Muitos).

## Tecnologias Utilizadas

- Java 8
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL 8
- Bean Validation
- Maven

## Pré-requisitos

- Java 8 (JDK 1.8)
- MySQL instalado e rodando
- Maven

## Como Executar o Projeto

### 1. Criar o banco de dados no MySQL

```sql
CREATE DATABASE clinica_medica;
```

### 2. Configurar as credenciais

Edite o arquivo `src/main/resources/application.properties` e altere conforme seu ambiente:

```properties
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação iniciará na porta **8080**. As tabelas são criadas automaticamente pelo Hibernate.

---

## Endpoints Disponíveis

### Médicos (`/medicos`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/medicos` | Cadastrar médico |
| GET | `/medicos` | Listar todos os médicos |
| GET | `/medicos/{id}` | Buscar médico por ID |
| PUT | `/medicos/{id}` | Atualizar médico |
| DELETE | `/medicos/{id}` | Remover médico |
| POST | `/medicos/{medicoId}/especialidades/{especialidadeId}` | Adicionar especialidade ao médico |
| DELETE | `/medicos/{medicoId}/especialidades/{especialidadeId}` | Remover especialidade do médico |

### Especialidades (`/especialidades`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/especialidades` | Cadastrar especialidade |
| GET | `/especialidades` | Listar todas as especialidades |
| GET | `/especialidades/{id}` | Buscar especialidade por ID |
| PUT | `/especialidades/{id}` | Atualizar especialidade |
| DELETE | `/especialidades/{id}` | Remover especialidade |

---

## Exemplos de Requisições

### Cadastrar Especialidade

**POST** `http://localhost:8080/especialidades`

```json
{
  "nome": "Cardiologia",
  "descricao": "Especialidade focada no coração e sistema cardiovascular"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "Cardiologia",
  "descricao": "Especialidade focada no coração e sistema cardiovascular",
  "medicos": []
}
```

---

### Cadastrar Médico

**POST** `http://localhost:8080/medicos`

```json
{
  "nome": "Dr. João Silva",
  "crm": "CRM/SP 12345",
  "email": "joao.silva@clinica.com",
  "telefone": "11999998888"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "Dr. João Silva",
  "crm": "CRM/SP 12345",
  "email": "joao.silva@clinica.com",
  "telefone": "11999998888",
  "especialidades": []
}
```

---

### Vincular Médico a uma Especialidade

**POST** `http://localhost:8080/medicos/1/especialidades/1`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "Dr. João Silva",
  "crm": "CRM/SP 12345",
  "email": "joao.silva@clinica.com",
  "telefone": "11999998888",
  "especialidades": [
    { "id": 1, "nome": "Cardiologia" }
  ]
}
```

---

### Listar Todos os Médicos

**GET** `http://localhost:8080/medicos`

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "Dr. João Silva",
    "crm": "CRM/SP 12345",
    "email": "joao.silva@clinica.com",
    "telefone": "11999998888",
    "especialidades": [
      { "id": 1, "nome": "Cardiologia" }
    ]
  }
]
```

---

### Atualizar Médico

**PUT** `http://localhost:8080/medicos/1`

```json
{
  "nome": "Dr. João Silva Junior",
  "crm": "CRM/SP 12345",
  "email": "joao.silva@clinica.com",
  "telefone": "11988887777"
}
```

---

### Exemplo de Erro de Validação

**POST** `http://localhost:8080/medicos` (com dados inválidos)

```json
{
  "nome": "",
  "crm": "",
  "email": "email-invalido"
}
```

**Resposta (400 Bad Request):**
```json
{
  "timestamp": "2026-05-12T10:30:00",
  "status": 400,
  "erro": "Dados inválidos",
  "campos": {
    "nome": "Nome é obrigatório",
    "crm": "CRM é obrigatório",
    "email": "Email inválido"
  }
}
```
