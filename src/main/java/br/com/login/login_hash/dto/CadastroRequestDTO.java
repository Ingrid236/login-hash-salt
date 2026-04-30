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
 * DTO para recebimento de dados de cadastro de novo usuário.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class CadastroRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 100, message = "O username deve ter entre 3 e 100 caracteres")
    private String username;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    private String senha;

    public CadastroRequestDTO(
            @JsonProperty("nome") String nome,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("senha") String senha) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
    }

    public static CadastroRequestDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, CadastroRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para CadastroRequestDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter CadastroRequestDTO para JSON", e);
        }
    }
}
