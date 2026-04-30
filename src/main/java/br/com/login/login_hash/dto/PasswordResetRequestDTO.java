package br.com.login.login_hash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para redefinição de senha utilizando um token.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class PasswordResetRequestDTO {

    @NotBlank(message = "O token é obrigatório")
    private String token;

    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 8, max = 100, message = "A nova senha deve ter entre 8 e 100 caracteres")
    private String novaSenha;

    public PasswordResetRequestDTO(
            @JsonProperty("token") String token,
            @JsonProperty("novaSenha") String novaSenha) {
        this.token = token;
        this.novaSenha = novaSenha;
    }

    public static PasswordResetRequestDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, PasswordResetRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para PasswordResetRequestDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter PasswordResetRequestDTO para JSON", e);
        }
    }
}
