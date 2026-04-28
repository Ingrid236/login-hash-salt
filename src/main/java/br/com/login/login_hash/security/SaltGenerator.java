package br.com.login.login_hash.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Responsável exclusivamente pela geração de salts criptográficos.
 * Responsabilidade única: gerar valores aleatórios seguros para uso como salt.
 */
@Component
public class SaltGenerator {

    private static final int SALT_LENGTH_BYTES = 32;
    private final SecureRandom secureRandom;

    public SaltGenerator() {
        this.secureRandom = new SecureRandom();
    }

    /**
     * Gera um salt aleatório seguro codificado em Base64.
     *
     * @return string Base64 representando o salt gerado
     */
    public String gerarSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
