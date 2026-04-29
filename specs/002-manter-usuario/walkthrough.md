# Walkthrough: Manter Usuário

Implementação completa dos casos de uso de gestão de perfil para usuários autenticados, seguindo os princípios de Clean Code e RCP.

## 🚀 O que foi implementado

### 1. Camada de Dados & Segurança
- **Filtro JWT Hardened**: O `JwtAuthenticationFilter` foi atualizado para verificar o campo `active` do usuário. Tokens de usuários desativados (Soft Delete) agora são rejeitados automaticamente.
- **Repository Enhancement**: Adicionado suporte no `UsuarioRepository` para validar unicidade de e-mail ignorando o próprio ID do usuário em edições.

### 2. Camada de Serviço (`UserService`)
- **Extração de Identidade**: Centralização da recuperação do usuário logado via `SecurityContextHolder`.
- **Lógica de Negócio**:
  - **Visualização**: Mapeamento seguro para DTO de resposta.
  - **Atualização**: Validação de e-mail duplicado e persistência de dados básicos.
  - **Senha**: Validação de senha atual via `BCrypt` antes da troca.
  - **Soft Delete**: Inativação lógica da conta.

### 3. Camada de Controle (`UserController`)
- Endpoints RESTful protegidos:
  - `GET /api/users/me`: Detalhes do perfil.
  - `PUT /api/users/me`: Atualização cadastral.
  - `PUT /api/users/me/password`: Troca de senha.
  - `DELETE /api/users/me`: Encerramento de conta.
- Documentação OpenAPI completa com definições de respostas e esquemas.

## 🛠️ Validação Técnica

### Endpoints Criados
| Método | Endpoint | Status Esperado | Descrição |
|--------|----------|-----------------|-----------|
| GET | `/api/users/me` | 200 OK | Retorna Perfil |
| PUT | `/api/users/me` | 200 OK | Atualiza Nome/Email |
| PUT | `/api/users/me/password` | 204 No Content | Altera Senha |
| DELETE | `/api/users/me` | 204 No Content | Soft Delete |

### Segurança
- ✅ **Isolamento**: Todas as operações utilizam o ID extraído do Token, prevenindo IDOR.
- ✅ **Proteção Inativo**: Usuários com `active=false` não conseguem mais autenticar.

## 📂 Arquivos Modificados/Criados
- [x] [UserService.java](file:///c:/segurança_auditoria/login-hash/src/main/java/br/com/login/login_hash/service/UserService.java)
- [x] [UserController.java](file:///c:/segurança_auditoria/login-hash/src/main/java/br/com/login/login_hash/controller/UserController.java)
- [x] [UserProfileResponseDTO.java](file:///c:/segurança_auditoria/login-hash/src/main/java/br/com/login/login_hash/dto/UserProfileResponseDTO.java)
- [x] [UserUpdateRequestDTO.java](file:///c:/segurança_auditoria/login-hash/src/main/java/br/com/login/login_hash/dto/UserUpdateRequestDTO.java)
- [x] [UpdatePasswordRequestDTO.java](file:///c:/segurança_auditoria/login-hash/src/main/java/br/com/login/login_hash/dto/UpdatePasswordRequestDTO.java)
- [x] [JwtAuthenticationFilter.java](file:///c:/segurança_auditoria/login-hash/src/main/java/br/com/login/login_hash/security/JwtAuthenticationFilter.java)
- [x] [UsuarioRepository.java](file:///c:/segurança_auditoria/login-hash/src/main/java/br/com/login/login_hash/repository/UsuarioRepository.java)
