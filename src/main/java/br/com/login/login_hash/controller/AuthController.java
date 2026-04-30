package br.com.login.login_hash.controller;

import br.com.login.login_hash.dto.ApiResponseDTO;
import br.com.login.login_hash.dto.CadastroRequestDTO;
import br.com.login.login_hash.dto.LoginRequestDTO;
import br.com.login.login_hash.service.CadastroService;
import br.com.login.login_hash.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para operações de autenticação.
 * Responsabilidade única: receber requisições HTTP e delegar para os serviços.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CadastroService cadastroService;
    private final LoginService loginService;
    private final br.com.login.login_hash.service.PasswordResetService passwordResetService;

    /**
     * Endpoint para cadastro de novo usuário.
     * POST /api/auth/cadastro
     *
     * @param request dados de cadastro validados
     * @return resposta com status 201 (Created) em caso de sucesso
     */
    @PostMapping("/cadastro")
    public ResponseEntity<ApiResponseDTO> cadastrar(@Valid @RequestBody CadastroRequestDTO request) {
        cadastroService.cadastrar(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.sucesso("Cadastro realizado com sucesso!"));
    }

    /**
     * Endpoint para login do usuário.
     * POST /api/auth/login
     *
     * @param request dados de login validados
     * @return resposta com status 200 (OK) em caso de sucesso
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        String token = loginService.autenticar(request);
        return ResponseEntity
                .ok(ApiResponseDTO.sucesso("Login realizado com sucesso", token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponseDTO> forgotPassword(@Valid @RequestBody br.com.login.login_hash.dto.ForgotPasswordRequestDTO request) {
        passwordResetService.generateResetToken(request.getEmail());
        return ResponseEntity.ok(ApiResponseDTO.sucesso("Se o email existir, um link de recuperação será enviado."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDTO> resetPassword(@Valid @RequestBody br.com.login.login_hash.dto.PasswordResetRequestDTO request) {
        passwordResetService.resetPassword(request.getToken(), request.getNovaSenha());
        return ResponseEntity.ok(ApiResponseDTO.sucesso("Senha alterada com sucesso."));
    }
}
