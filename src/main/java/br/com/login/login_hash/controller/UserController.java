package br.com.login.login_hash.controller;

import br.com.login.login_hash.dto.UpdatePasswordRequestDTO;
import br.com.login.login_hash.dto.UserProfileResponseDTO;
import br.com.login.login_hash.dto.UserUpdateRequestDTO;
import br.com.login.login_hash.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para operações relacionadas ao usuário logado.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints para gestão de perfil do usuário")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Obter perfil do usuário", description = "Retorna os dados do usuário autenticado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil recuperado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileResponseDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    })
    public ResponseEntity<UserProfileResponseDTO> getMe() {
        return ResponseEntity.ok(userService.getLoggedUserProfile());
    }

    @PutMapping("/me")
    @Operation(summary = "Atualizar perfil do usuário", description = "Atualiza nome e email do usuário autenticado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já em uso", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    })
    public ResponseEntity<UserProfileResponseDTO> updateMe(@Valid @RequestBody UserUpdateRequestDTO request) {
        return ResponseEntity.ok(userService.updateUserProfile(request));
    }

    @PutMapping("/me/password")
    @Operation(summary = "Atualizar senha do usuário", description = "Altera a senha do usuário autenticado validando a senha atual", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Senha atual incorreta ou nova senha inválida", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    })
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequestDTO request) {
        userService.updatePassword(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    @Operation(summary = "Excluir conta", description = "Desativa a conta do usuário autenticado (Soft Delete)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta desativada com sucesso", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    })
    public ResponseEntity<Void> deleteMe() {
        userService.deleteLoggedUser();
        return ResponseEntity.noContent().build();
    }
}
