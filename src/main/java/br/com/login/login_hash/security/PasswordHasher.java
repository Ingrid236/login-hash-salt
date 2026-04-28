package br.com.login.login_hash.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Responsável exclusivamente pelo hashing de senhas.
 * Responsabilidade única: gerar e verificar hashes de senha usando SHA-256.
 *
 * O processo combina senha + salt e aplica o algoritmo SHA-256,
 * retornando o resultado codificado em Base64.
 */
@Component
public class PasswordHasher {

    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Gera o hash da senha combinada com o salt.
     *
     * @param senha a senha em texto puro
     * @param salt  o salt a ser combinado com a senha
     * @return hash da combinação senha+salt codificado em Base64
     */
    public String gerarHash(String senha, String salt) {
        String senhaComSalt = senha + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(senhaComSalt.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo de hash não disponível: " + HASH_ALGORITHM, e);
        }
    }

    /**
     * Verifica se a senha digitada, combinada com o salt, corresponde ao hash armazenado.
     *
     * @param senhaDigitada senha informada pelo usuário
     * @param salt          salt armazenado no banco
     * @param hashArmazenado hash armazenado no banco
     * @return true se o hash gerado é igual ao armazenado
     */
    public boolean verificarSenha(String senhaDigitada, String salt, String hashArmazenado) {
        String hashGerado = gerarHash(senhaDigitada, salt);
        return hashGerado.equals(hashArmazenado);
    }
}
