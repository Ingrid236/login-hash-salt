package br.com.login.login_hash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para receber os dados de login do usuário.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

    @NotBlank(message = "O identificador (email ou username) é obrigatório")
    private String identifier;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    public LoginRequestDTO(
            @JsonProperty("identifier") String identifier,
            @JsonProperty("senha") String senha) {
        this.identifier = identifier;
        this.senha = senha;
    }

    public static LoginRequestDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, LoginRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para LoginRequestDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter LoginRequestDTO para JSON", e);
        }
    }
}
