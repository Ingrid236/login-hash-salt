# Data Model: Manter Usuário

O caso de uso de "Manter Usuário" não introduz novas entidades de banco de dados, mas utiliza a entidade `Usuario` existente. 
As alterações de estado e validações aplicadas são detalhadas abaixo.

## Entities

### `Usuario` (Existente)

| Field | Type | Attributes | Description / Validation |
|-------|------|------------|---------------------------|
| `id` | Long | PK, Auto | Identificador único. |
| `nome` | String | Not Null | Nome do usuário. Pode ser modificado. Length: Max 100. |
| `username` | String | Unique, Not Null | Nome de usuário. **IMUTÁVEL** após criação (FR-002). Length: Max 100. |
| `email` | String | Unique, Not Null | E-mail do usuário. Pode ser modificado, exigindo validação de formato e unicidade global. Length: Max 100. |
| `senhaHash` | String | Not Null | Hash BCrypt da senha. Atualizado pelo endpoint de alterar senha. Length: Max 255. |
| `active` | boolean | Not Null | Status da conta. Modificado para `false` no Soft Delete. |

## Validation Rules

1. **Email Uniqueness (FR-003)**: Ao atualizar o e-mail, o sistema deve verificar se o novo e-mail já pertence a outro usuário (excluindo a si mesmo).
2. **Current Password Matching (FR-006)**: Para alterar a senha, o usuário deve informar a senha atual. O hash BCrypt gerado a partir do input deve corresponder ao `senhaHash` gravado no banco.

## State Transitions

- `ACTIVE` -> `INACTIVE`: Disparado pela deleção da conta. O campo `active` é setado de `true` para `false`. A sessão/JWT atual deixa de ter validade (ou deve ser bloqueado nos filtros).
