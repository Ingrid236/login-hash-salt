package br.com.login.login_hash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para receber dados de atualização de perfil (nome e email).
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserUpdateRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    public UserUpdateRequestDTO(
            @JsonProperty("nome") String nome,
            @JsonProperty("email") String email) {
        this.nome = nome;
        this.email = email;
    }

    public static UserUpdateRequestDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, UserUpdateRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para UserUpdateRequestDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter UserUpdateRequestDTO para JSON", e);
        }
    }
}
