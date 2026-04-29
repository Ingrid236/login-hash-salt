# Research: Manter Usuário

## Soft Delete Strategy
- **Decision**: Utilizar o campo `active` (boolean) já existente na entidade `Usuario` (`Usuario.java`) para implementar o Soft Delete.
- **Rationale**: O campo já está mapeado no banco de dados e na classe JPA. Inativar a conta requer apenas um `usuario.setActive(false)`. É a abordagem mais simples e eficiente, sem necessidade de migrations adicionais no banco.
- **Alternatives considered**:
  - Criar um enum `Status` (ACTIVE, DELETED). Rejeitado porque demandaria alteração estrutural no banco sem benefício imediato sobre o campo boolean existente.

## Auth Identity Extraction
- **Decision**: Extrair a identidade do usuário (username ou id) a partir do `SecurityContextHolder.getContext().getAuthentication()`.
- **Rationale**: Impede vulnerabilidades IDOR (Insecure Direct Object Reference) garantindo que as operações de visualização, alteração e deleção ocorram apenas na conta associada ao token JWT autenticado no request.
- **Alternatives considered**:
  - Passar o ID do usuário como PathVariable (ex: `/api/users/{id}`). Rejeitado por ser inseguro caso o controle de autorização falhe.
