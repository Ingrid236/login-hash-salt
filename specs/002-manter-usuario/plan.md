# Implementation Plan: Manter Usuário

**Branch**: `002-manter-usuario` | **Date**: 2026-04-29 | **Spec**: [specs/002-manter-usuario/spec.md](specs/002-manter-usuario/spec.md)
**Input**: Feature specification from `/specs/002-manter-usuario/spec.md`

## Summary

Implementar a API de gestão de perfil (visualizar, atualizar dados, atualizar senha, e soft delete) para o sistema de autenticação, garantindo que todas as ações sejam isoladas através da extração segura da identidade do token JWT, preservando a imutabilidade do username e respeitando os princípios de Clean Code (SRP, MVC) e Separação de Preocupações.

## Technical Context

**Language/Version**: Java 17+ (JDK 24 used in env) / Spring Boot 3/4
**Primary Dependencies**: Spring Web, Spring Security, Spring Data JPA, PostgreSQL, JJWT, BCrypt
**Storage**: PostgreSQL
**Testing**: JUnit 5, Mockito (MockMvc para testes de integração)
**Target Platform**: Server
**Project Type**: REST Web Service (Backend)
**Performance Goals**: < 1 second response time para consultas/atualizações (SC-002)
**Constraints**: Operações apenas na própria conta (identidade via JWT), Soft Delete, Username inalterável
**Scale/Scope**: 1 Feature Module (UserController, UserService, DTOs)

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **I. Separation of Concerns**: PASSED. O projeto implementará apenas endpoints REST devolvendo JSON (DTOs), sem regras visuais.
- **II. Clean Code & Architecture**: PASSED. Será utilizado o padrão DTO para Entrada/Saída, Controller para roteamento, Service para regras de negócio (Soft Delete, validação BCrypt) e Repository para persistência.
- **III. Secure Auth & Persistence**: PASSED. A atualização de senha utilizará BCrypt para hashing antes de persistir no banco de dados.
- **IV. Backend Dominance**: PASSED. Validações de unicidade de email e correspondência de senhas antigas serão feitas pelo backend.
- **V. Stateless Security**: PASSED. A identidade será sempre extraída do SecurityContextHolder preenchido pelo filtro JWT (não via IDs na URL).

## Project Structure

### Documentation (this feature)

```text
specs/002-manter-usuario/
├── plan.md              
├── research.md          
├── data-model.md        
├── quickstart.md        
├── contracts/           
└── tasks.md             
```

### Source Code (repository root)

```text
src/main/java/br/com/login/login_hash/
├── controller/
│   └── UserController.java
├── dto/
│   ├── UserProfileResponseDTO.java
│   ├── UserUpdateRequestDTO.java
│   └── UpdatePasswordRequestDTO.java
├── model/
│   └── User.java (Existente - necessita suporte a status)
├── repository/
│   └── UserRepository.java (Existente)
└── service/
    └── UserService.java (Existente/Novo)
```

**Structure Decision**: Option 1 (Single Project / Spring Boot MVC structure) utilizing Controller-Service-Repository pattern.

## Complexity Tracking

*(No constitution violations, therefore no justifications needed)*
