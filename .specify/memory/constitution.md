<!--
Sync Impact Report:
- Version change: 1.0.0 -> 1.1.0
- Modified principles:
  - Added V. Stateless Security & Identity
- Added sections: None
- Removed sections: None
- Templates requiring updates:
  - ⚠ .specify/templates/plan-template.md
  - ⚠ .specify/templates/spec-template.md
  - ⚠ .specify/templates/tasks-template.md
- Follow-up TODOs: None
-->

# Java Login Hash System Constitution

## Core Principles

### I. Separation of Concerns (Frontend/Backend)
The backend exclusively handles business logic, security, and data persistence. The frontend is strictly responsible for the user interface and presentation. No business logic shall reside in the frontend.

### II. Clean Code & Architectural Organization
The codebase must adhere to strict Clean Code norms and architectural patterns (e.g., SRP, MVC/Clean Architecture) to ensure high cohesion and low coupling. All structures must be highly organized.

### III. Secure Authentication & Data Persistence
User passwords must never be stored in plain text. The application must utilize industry-standard cryptographic hashing algorithms with salt (e.g., BCrypt, Argon2) for persisting user credentials in the database.

### IV. Backend Dominance
Any remote communication must be handled through clear interfaces. The backend is the single source of truth for the application's state, logic, and security validations.

### V. Stateless Security & Identity
The system relies on stateless JSON Web Tokens (JWT) for session management. Security against brute-force attacks must be inherently handled via progressive rate limiting. The system universally supports authentication via either Email or Username.

## Additional Constraints

- **Language & Tech Stack**: Java for the backend services.
- **Database**: Must use a secure database setup to persist users and passwords.
- **Security**: Must implement Hash & Salt mechanisms for all password operations. Vulnerabilities such as SQL Injection or insecure data storage must be strictly avoided. Secure time-bound tokens must be used for any reset/verification flows.

## Development Workflow

- Code must pass through code review ensuring Clean Code standards are meticulously met.
- Security audits on authentication mechanisms must be performed before any integration or deployment.

## Governance

All PRs/reviews must verify compliance with the Core Principles. Security protocols (Hash, Salt, JWT, Rate Limiting) are non-negotiable and must be verified by automated tests or rigorous code review.

**Version**: 1.1.0 | **Ratified**: 2026-04-28 | **Last Amended**: 2026-04-28
