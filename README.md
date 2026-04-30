# Login Hash & Salt - Sistema de Autenticação Segura

Este projeto é uma aplicação Full Stack desenvolvida para demonstrar e implementar práticas modernas de segurança em sistemas de autenticação, focando em proteção contra ataques de força bruta, integridade de senhas e comunicação segura entre camadas.

## 🚀 Sobre o Projeto

O sistema oferece um fluxo completo de gerenciamento de usuários, incluindo:
- **Cadastro de Usuários**: Armazenamento seguro de senhas usando BCrypt com Salt.
- **Autenticação JWT**: Sessões stateless com tokens de acesso seguros.
- **Rate Limiting**: Proteção contra ataques de força bruta no login, limitando tentativas por IP.
- **Documentação Interativa**: Swagger UI para testes rápidos da API.
- **Frontend Moderno**: Interface em Angular com design minimalista e responsivo.

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 24** (ou superior)
- **Spring Boot 3.x**
- **Spring Security** (JWT + BCrypt)
- **Hibernate / JPA**
- **Bucket4j** (Controle de fluxo/Rate Limiting)
- **PostgreSQL** (Banco de Dados)

### Frontend
- **Angular 19**
- **Angular Material**
- **OpenAPI Generator** (Integração automática com a API)

---

## ⚙️ Configuração do Banco de Dados (PostgreSQL)

Para rodar este projeto na sua máquina local, você precisará configurar o PostgreSQL. Siga os passos abaixo:

### 1. Requisitos
Certifique-se de ter o PostgreSQL instalado e rodando na porta padrão `5432`.

### 2. Criar o Banco de Dados
Abra o seu terminal (ou ferramenta como pgAdmin/DBeaver) e execute o comando:
```sql
CREATE DATABASE login_hash_db;
```

### 3. Credenciais Padrão
O projeto está configurado para utilizar as seguintes credenciais em `src/main/resources/application.properties`:
- **URL**: `jdbc:postgresql://localhost:5432/login_hash_db`
- **Usuário**: `postgres`
- **Senha**: `12345`

> **Nota:** Se você utiliza uma senha diferente ou outra porta, altere os campos `spring.datasource.username` e `spring.datasource.password` no arquivo `application.properties`.

---

## 🏃 Como Rodar a Aplicação

### Backend (Java)
1. Certifique-se de que o banco de dados `login_hash_db` foi criado.
2. Navegue até a pasta raiz do projeto backend.
3. Execute o comando:
   ```bash
   ./mvnw spring-boot:run
   ```
4. A API estará disponível em: `http://localhost:8081`
5. Acesse a documentação em: `http://localhost:8081/swagger-ui.html`

### Frontend (Angular)
1. Navegue até a pasta do projeto frontend.
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Inicie o servidor de desenvolvimento:
   ```bash
   npm start
   ```
4. Acesse o sistema em: `http://localhost:4200`

---

## 🛡️ Segurança Implementada
- **Hashing**: Senhas nunca são armazenadas em texto limpo. Utilizamos `BCryptPasswordEncoder` que gera automaticamente um Salt único para cada senha.
- **JWT**: O token contém informações do usuário e expira em 1 hora.
- **CORS**: Configurado para permitir apenas a origem do frontend local.
- **Bucket4j**: Filtro de segurança que bloqueia IPs que tentarem mais de 5 logins por minuto.
