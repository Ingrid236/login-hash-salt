package br.com.login.login_hash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para solicitação de recuperação de senha.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ForgotPasswordRequestDTO {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    public ForgotPasswordRequestDTO(@JsonProperty("email") String email) {
        this.email = email;
    }

    public static ForgotPasswordRequestDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, ForgotPasswordRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para ForgotPasswordRequestDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter ForgotPasswordRequestDTO para JSON", e);
        }
    }
}
