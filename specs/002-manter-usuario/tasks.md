# Tasks: Manter Usuário

**Feature**: Manter Usuário
**Spec**: [specs/002-manter-usuario/spec.md](spec.md)
**Plan**: [specs/002-manter-usuario/plan.md](plan.md)

## 📌 Implementation Strategy

- **MVP**: Entrega da User Story 1 (Visualizar Perfil), garantindo a recuperação segura da identidade via Token JWT.
- **Incremental Delivery**: Adição gradual das operações de mutação (Atualizar Dados e Senha) e por fim, a funcionalidade destrutiva de Soft Delete (Excluir Conta).

## 🚀 Phase 1: Setup

*Project initialization and scaffolding*

- [x] T001 [P] Create DTOs (`UserProfileResponseDTO.java`, `UserUpdateRequestDTO.java`, `UpdatePasswordRequestDTO.java`) in `src/main/java/br/com/login/login_hash/dto/`

## 🏗️ Phase 2: Foundational

*Blocking prerequisites for all user stories*

- [x] T002 Update `JwtAuthenticationFilter` in `src/main/java/br/com/login/login_hash/security/` to validate if `usuario.isActive() == true` before setting the Security Context.

## 👥 Phase 3: User Story 1 - Visualizar Perfil (Priority: P1)

**Goal**: O usuário autenticado deseja visualizar os detalhes do seu próprio perfil para conferir se as informações estão corretas.
**Independent Test Criteria**: Enviar uma requisição GET para `/api/users/me` com um token válido retorna um JSON 200 OK com os dados do usuário. Sem o token, retorna 401.

- [x] T003 [US1] Create or update `UserService` in `src/main/java/br/com/login/login_hash/service/UserService.java` to implement a method `getLoggedUserProfile()` that securely extracts the identity from `SecurityContextHolder`.
- [x] T004 [US1] Create `UserController` in `src/main/java/br/com/login/login_hash/controller/UserController.java` mapping `GET /api/users/me`.

## 👥 Phase 4: User Story 2 - Atualizar Dados Pessoais (Priority: P1)

**Goal**: O usuário autenticado deseja alterar seus dados cadastrais (nome e e-mail).
**Independent Test Criteria**: Enviar PUT para `/api/users/me` altera com sucesso. Tentar e-mail existente em outra conta retorna erro 400 Bad Request.

- [x] T005 [P] [US2] Add `boolean existsByEmailAndIdNot(String email, Long id)` to `UserRepository.java` in `src/main/java/br/com/login/login_hash/repository/`.
- [x] T006 [US2] Add `updateUserProfile(UserUpdateRequestDTO)` method to `UserService.java`, enforcing email uniqueness validation.
- [x] T007 [US2] Add `PUT /api/users/me` endpoint mapping to `UserController.java`.

## 👥 Phase 5: User Story 3 - Atualizar Senha (Priority: P1)

**Goal**: O usuário autenticado deseja alterar sua senha informando a senha atual.
**Independent Test Criteria**: Enviar a senha atual e a nova. Um login subsequente com a nova senha deve funcionar, e com a antiga deve falhar.

- [x] T008 [US3] Add `updatePassword(UpdatePasswordRequestDTO)` to `UserService.java`, verifying current password via `PasswordEncoder` and hashing the new one.
- [x] T009 [US3] Add `PUT /api/users/me/password` endpoint mapping to `UserController.java`.

## 👥 Phase 6: User Story 4 - Excluir Conta (Priority: P2)

**Goal**: O usuário autenticado deseja excluir sua própria conta da plataforma.
**Independent Test Criteria**: Após enviar um DELETE para a rota, o token atual passa a ser inválido em rotas seguras e o usuário não consegue mais logar (Soft Delete).

- [x] T010 [US4] Add `deleteLoggedUser()` to `UserService.java` implementing Soft Delete (`setActive(false)`).
- [x] T011 [US4] Add `DELETE /api/users/me` endpoint mapping to `UserController.java`.

## ✨ Final Phase: Polish & Cross-Cutting Concerns

- [x] T012 Add OpenAPI/Swagger `@Operation` and `@ApiResponses` annotations across all endpoints in `UserController.java`.

---

## 📊 Dependencies & Parallel Execution

**Dependency Graph**:
- T002 must be completed before T010 (to ensure Soft Delete actually blocks access).
- T003 must be completed before T004.
- T005 -> T006 -> T007.
- T008 -> T009.
- T010 -> T011.

**Parallel Opportunities**:
- **US2 (T005-T007)** and **US3 (T008-T009)** can be implemented in parallel by different agents as they deal with completely separate aspects of `UserService`.
- **T001** (DTO creation) can be done independently immediately.
