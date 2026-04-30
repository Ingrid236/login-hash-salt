package br.com.login.login_hash.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO genérico para respostas da API.
 * Responsabilidade única: padronizar o formato de resposta da API.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO {

    private boolean sucesso;
    private String mensagem;
    private Object dados;
    private LocalDateTime timestamp;

    public ApiResponseDTO(
            @JsonProperty("sucesso") boolean sucesso,
            @JsonProperty("mensagem") String mensagem,
            @JsonProperty("dados") Object dados,
            @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static ApiResponseDTO fromJson(String json) {
        try {
            return new ObjectMapper().findAndRegisterModules().readValue(json, ApiResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para ApiResponseDTO", e);
        }
    }

    public String toJson() {
        try {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter ApiResponseDTO para JSON", e);
        }
    }

    /**
     * Cria uma resposta de sucesso básica.
     *
     * @param mensagem mensagem de sucesso
     * @return ApiResponseDTO configurado para sucesso
     */
    public static ApiResponseDTO sucesso(String mensagem) {
        return new ApiResponseDTO(true, mensagem, null, LocalDateTime.now());
    }

    /**
     * Cria uma resposta de sucesso com dados.
     *
     * @param mensagem mensagem de sucesso
     * @param dados dados a serem retornados
     * @return ApiResponseDTO configurado para sucesso com dados
     */
    public static ApiResponseDTO sucesso(String mensagem, Object dados) {
        return new ApiResponseDTO(true, mensagem, dados, LocalDateTime.now());
    }

    /**
     * Cria uma resposta de erro.
     *
     * @param mensagem mensagem de erro
     * @return ApiResponseDTO configurado para erro
     */
    public static ApiResponseDTO erro(String mensagem) {
        return new ApiResponseDTO(false, mensagem, null, LocalDateTime.now());
    }
}
