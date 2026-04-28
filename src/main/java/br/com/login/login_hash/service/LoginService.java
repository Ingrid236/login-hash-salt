package br.com.login.login_hash.service;

import br.com.login.login_hash.dto.LoginRequestDTO;
import br.com.login.login_hash.entity.Usuario;
import br.com.login.login_hash.exception.CredenciaisInvalidasException;
import br.com.login.login_hash.repository.UsuarioRepository;
import br.com.login.login_hash.security.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela autenticação de usuários.
 * Responsabilidade única: orquestrar o fluxo de login
 * (busca por email, verificação de hash e validação de credenciais).
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;

    /**
     * Realiza a autenticação do usuário.
     *
     * @param request dados de login (email e senha)
     * @return o usuário autenticado
     * @throws CredenciaisInvalidasException se email não existir ou senha não conferir
     */
    public Usuario autenticar(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(CredenciaisInvalidasException::new);

        boolean senhaValida = passwordHasher.verificarSenha(
                request.getSenha(),
                usuario.getSalt(),
                usuario.getSenhaHash()
        );

        if (!senhaValida) {
            throw new CredenciaisInvalidasException();
        }

        return usuario;
    }
}
