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
 * DTO para atualização de senha do usuário autenticado.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UpdatePasswordRequestDTO {

    @NotBlank(message = "A senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 8, max = 100, message = "A nova senha deve ter entre 8 e 100 caracteres")
    private String novaSenha;

    public UpdatePasswordRequestDTO(
            @JsonProperty("senhaAtual") String senhaAtual,
            @JsonProperty("novaSenha") String novaSenha) {
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
    }

    public static UpdatePasswordRequestDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, UpdatePasswordRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para UpdatePasswordRequestDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter UpdatePasswordRequestDTO para JSON", e);
        }
    }
}
