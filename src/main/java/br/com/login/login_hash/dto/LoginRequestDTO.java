package br.com.login.login_hash.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para receber os dados de login do usuário.
 * Responsabilidade única: transportar e validar dados de entrada do login.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "O identificador (email ou username) é obrigatório")
    private String identifier;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}
