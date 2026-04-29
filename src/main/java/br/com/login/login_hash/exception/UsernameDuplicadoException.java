package br.com.login.login_hash.exception;

/**
 * Exceção lançada quando o username já está em uso.
 */
public class UsernameDuplicadoException extends RuntimeException {
    public UsernameDuplicadoException(String username) {
        super("Username já está em uso: " + username);
    }
}
