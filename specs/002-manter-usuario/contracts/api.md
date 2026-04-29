# API Contracts: Manter Usuário

Todas as requisições requerem autenticação (cabeçalho `Authorization: Bearer <token>`). O identificador do usuário é **sempre** derivado do token, ignorando eventuais IDs no corpo ou na URL.

## 1. Visualizar Perfil
**GET** `/api/users/me`

**Response (200 OK):**
```json
{
  "nome": "string",
  "username": "string",
  "email": "string"
}
```

## 2. Atualizar Dados Pessoais
**PUT** `/api/users/me`

**Request Body:**
```json
{
  "nome": "string",  // Requerido. Mínimo 2 caracteres.
  "email": "string"  // Requerido. Formato válido de email.
}
```
*(Nota: O username não está no payload pois é imutável)*

**Response (200 OK):**
```json
{
  "nome": "string",
  "username": "string",
  "email": "string"
}
```

**Response (400 Bad Request):**
```json
{
  "error": "Email já está em uso" // ou erro de validação de campos
}
```

## 3. Atualizar Senha
**PUT** `/api/users/me/password`

**Request Body:**
```json
{
  "senhaAtual": "string", // Requerido
  "novaSenha": "string"   // Requerido, mínimo de 6 caracteres
}
```

**Response (200 OK):** Vazio (ou mensagem de sucesso)

**Response (400 Bad Request):**
```json
{
  "error": "A senha atual está incorreta"
}
```

## 4. Excluir Conta (Soft Delete)
**DELETE** `/api/users/me`

**Response (204 No Content):** Sucesso na desativação da conta.
*(Qualquer requisição subsequente com o token JWT deverá ser recusada pelo fato da conta estar inativa, mas no mínimo o login será bloqueado)*
