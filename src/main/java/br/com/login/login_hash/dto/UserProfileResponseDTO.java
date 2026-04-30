package br.com.login.login_hash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para exibir dados básicos do perfil do usuário.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserProfileResponseDTO {

    private String nome;
    private String username;
    private String email;

    public UserProfileResponseDTO(
            @JsonProperty("nome") String nome,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email) {
        this.nome = nome;
        this.username = username;
        this.email = email;
    }

    public static UserProfileResponseDTO fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, UserProfileResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para UserProfileResponseDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter UserProfileResponseDTO para JSON", e);
        }
    }
}
