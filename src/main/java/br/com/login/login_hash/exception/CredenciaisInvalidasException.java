package br.com.login.login_hash.exception;

/**
 * Exceção lançada quando as credenciais de login são inválidas.
 * Responsabilidade única: representar o erro de credenciais inválidas.
 */
public class CredenciaisInvalidasException extends RuntimeException {

    public CredenciaisInvalidasException() {
        super("Email ou senha inválidos");
    }
}
