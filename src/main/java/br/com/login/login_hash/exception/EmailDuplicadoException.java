package br.com.login.login_hash.exception;

/**
 * Exceção lançada quando um email já está cadastrado no sistema.
 * Responsabilidade única: representar o erro de email duplicado.
 */
public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String email) {
        super("O email já está cadastrado: " + email);
    }
}
