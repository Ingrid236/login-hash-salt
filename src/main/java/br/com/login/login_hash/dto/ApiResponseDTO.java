package br.com.login.login_hash.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO {

    private boolean sucesso;
    private String mensagem;
    private LocalDateTime timestamp;

    /**
     * Cria uma resposta de sucesso.
     *
     * @param mensagem mensagem de sucesso
     * @return ApiResponseDTO configurado para sucesso
     */
    public static ApiResponseDTO sucesso(String mensagem) {
        return new ApiResponseDTO(true, mensagem, LocalDateTime.now());
    }

    /**
     * Cria uma resposta de erro.
     *
     * @param mensagem mensagem de erro
     * @return ApiResponseDTO configurado para erro
     */
    public static ApiResponseDTO erro(String mensagem) {
        return new ApiResponseDTO(false, mensagem, LocalDateTime.now());
    }
}
