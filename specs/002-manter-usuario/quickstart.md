# Quickstart: Manter Usuário

## Setup
Este fluxo será totalmente dependente do ambiente Spring Boot atual e não exige infraestrutura ou scripts adicionais. 

1. **Database**: A tabela `usuario` já possui a coluna `active` e `senha_hash`. Nenhuma migração de banco de dados (`flyway` ou `liquibase`) é necessária para este caso de uso em específico.
2. **Dependências**: Utilize as já importadas (`spring-boot-starter-web`, `spring-boot-starter-security`, `spring-boot-starter-data-jpa`).

## Implementation Tasks (High-Level)

1. **DTOs**: Criar `UserProfileResponseDTO`, `UserUpdateRequestDTO`, `UpdatePasswordRequestDTO` no pacote de DTOs da aplicação.
2. **Service**:
   - Atualizar/Criar `UserService` ou utilizar o já existente.
   - Criar método `getLoggedUser()` que extrai a string identificadora (username/email) de `SecurityContextHolder.getContext().getAuthentication().getName()`.
   - Implementar validação da senha com `PasswordEncoder.matches(senhaAtual, usuario.getSenhaHash())`.
   - Na exclusão, apenas setar `usuario.setActive(false)` e salvar.
3. **Controller**: Criar `UserController` com os mappings `GET /api/users/me`, `PUT /api/users/me`, `PUT /api/users/me/password`, e `DELETE /api/users/me`.
4. **Security Filter**: Garantir que o `JwtAuthenticationFilter` também valide se o usuário recuperado do banco pelo token possui `active == true`. Caso seja `false`, rejeitar a autenticação. Isso previne que tokens não expirados de contas excluídas continuem válidos.

## Testing
- Envie um token Bearer válido para as rotas.
- Teste atualizar e-mail para um que já existe na base (deve dar erro 400).
- Teste exclusão e em seguida tente acessar o perfil (deve retornar 401 ou 403 dependendo do filtro).
