# Feature Specification: Manter Usuário

**Feature Branch**: `002-manter-usuario`  
**Created**: 2026-04-29  
**Status**: Draft  
**Input**: User description: "Como o Login já está um pouco estruturado, foque no desenvolvimento do caso de uso de manter usuário, faça de forma que respeite os principios de clean code, principalmente o de RCP."

## Clarifications

### Session 2026-04-29
- Q: Atualização de Senha → A: Incluir a funcionalidade de alterar senha para o usuário logado (exigindo senha atual e nova senha).
- Q: Alteração de Username → A: Não, o username é permanente e não pode ser editado.
- Q: Tipo de Exclusão de Conta → A: Soft Delete (exclusão lógica mudando o status para inativo/deletado para manter histórico).

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Visualizar Perfil (Priority: P1)

O usuário autenticado deseja visualizar os detalhes do seu próprio perfil para conferir se as informações (como nome, email e username) estão corretas.

**Why this priority**: É essencial para a transparência que o usuário saiba quais dados a plataforma detém sobre ele.

**Independent Test**: Can be fully tested by authenticating and requesting the profile data, delivering read-only user data.

**Acceptance Scenarios**:

1. **Given** um usuário logado na plataforma, **When** ele acessa a seção de perfil, **Then** o sistema exibe seus dados pessoais (nome, username, email).
2. **Given** um usuário não autenticado, **When** ele tenta acessar a seção de perfil, **Then** o sistema bloqueia o acesso e solicita login.

---

### User Story 2 - Atualizar Dados Pessoais (Priority: P1)

O usuário autenticado deseja alterar seus dados cadastrais (ex: corrigir um erro de digitação no nome) para manter seu perfil atualizado.

**Why this priority**: Usuários precisam manter suas informações de contato e identificação corretas.

**Independent Test**: Can be fully tested by submitting a data update request for an existing user.

**Acceptance Scenarios**:

1. **Given** um usuário autenticado na página de edição, **When** ele altera seu nome e salva, **Then** o sistema atualiza o registro e exibe a mensagem de sucesso.
2. **Given** um usuário autenticado, **When** ele tenta usar um email já associado a outra conta, **Then** o sistema recusa a alteração com uma mensagem de erro clara.

---

### User Story 3 - Atualizar Senha (Priority: P1)

O usuário autenticado deseja alterar sua senha por dentro do painel de perfil para manter a segurança da sua conta, sem precisar passar pelo fluxo de recuperação via e-mail.

**Why this priority**: Melhorar a segurança permitindo rotação de credenciais de forma prática.

**Independent Test**: Can be fully tested by submitting the current password and a new password, then attempting to log in with both to verify the change.

**Acceptance Scenarios**:

1. **Given** um usuário logado, **When** ele informa sua senha atual corretamente e a nova senha, **Then** o sistema atualiza a senha e retorna sucesso.
2. **Given** um usuário logado, **When** ele informa a senha atual incorretamente, **Then** o sistema rejeita a alteração com erro.

---

### User Story 4 - Excluir Conta (Priority: P2)

O usuário autenticado deseja excluir sua própria conta da plataforma para que seus dados não fiquem mais armazenados de forma ativa.

**Why this priority**: É um requisito importante para privacidade e controle do usuário permitir que ele revogue seu acesso e desative sua conta.

**Independent Test**: Can be fully tested by requesting account deletion and verifying the user can no longer log in.

**Acceptance Scenarios**:

1. **Given** um usuário autenticado, **When** ele confirma a exclusão da sua conta, **Then** o sistema altera o status da conta para 'inativo/excluído' (Soft Delete) e encerra a sessão ativa.

### Edge Cases

- What happens when o usuário tenta alterar seu email para um formato inválido? O sistema deve validar a entrada e retornar um erro descritivo antes de processar a alteração.
- How does system handle tentativas de acessar ou excluir o perfil de *outros* usuários? O sistema deve garantir que o usuário só pode operar sobre a própria conta autenticada, impedindo a manipulação de IDs arbitrários.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: O sistema MUST permitir que um usuário autenticado visualize seus próprios dados cadastrais.
- **FR-002**: O sistema MUST permitir que um usuário autenticado altere seu nome e email (o username é permanente e imutável).
- **FR-003**: O sistema MUST validar a unicidade do email caso o usuário tente alterá-lo.
- **FR-004**: O sistema MUST permitir que o usuário inative a própria conta (Soft Delete), revogando permanentemente seu acesso sem excluir o registro do banco.
- **FR-005**: O sistema MUST garantir o isolamento das informações, baseando as ações na identidade extraída da sessão (Token), impedindo acessos cruzados.
- **FR-006**: O sistema MUST permitir que um usuário autenticado altere sua própria senha, exigindo a validação da senha atual.

### Key Entities

- **Usuário (User)**: Representa a pessoa física utilizando o sistema, contendo nome, username, email e status.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Usuários conseguem recuperar, editar e deletar seus perfis com 100% de sucesso nos fluxos válidos.
- **SC-002**: Operações de leitura e atualização de perfil respondem em menos de 1 segundo para o usuário.
- **SC-003**: A implementação garante isolamento total (0 incidentes ou brechas identificadas de um usuário visualizando ou modificando a conta de outro).

## Assumptions

- Presume-se que o módulo de login e autenticação já existente será utilizado para identificar de forma segura qual é o usuário solicitando a ação.
- "RCP" mencionado no input foi interpretado como princípios arquiteturais de Separação de Preocupações e Clean Code, garantindo que o novo fluxo seja adequadamente dividido logicamente.
- A exclusão da conta será lógica (Soft Delete), preservando o registro para fins de auditoria, mas inativando o status.
