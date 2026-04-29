package br.com.login.login_hash.service;

import br.com.login.login_hash.dto.CadastroRequestDTO;
import br.com.login.login_hash.entity.Usuario;
import br.com.login.login_hash.exception.EmailDuplicadoException;
import br.com.login.login_hash.exception.UsernameDuplicadoException;
import br.com.login.login_hash.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pelo cadastro de novos usuários.
 * Responsabilidade única: orquestrar o fluxo de cadastro
 * (validação de duplicidade, hashing com BCrypt e persistência).
 */
@Service
@RequiredArgsConstructor
public class CadastroService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param request dados de cadastro validados
     * @return o usuário persistido
     */
    @Transactional
    public Usuario cadastrar(CadastroRequestDTO request) {
        validarUnicidade(request.getEmail(), request.getUsername());

        String senhaHash = passwordEncoder.encode(request.getSenha());

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(senhaHash);
        usuario.setActive(true);
        usuario.setEmailVerified(false);

        // Here we could publish an event or call a MailService to send verification
        // email

        return usuarioRepository.save(usuario);
    }

    /**
     * Valida se o email ou username já estão em uso.
     */
    private void validarUnicidade(String email, String username) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailDuplicadoException(email);
        }
        if (usuarioRepository.existsByUsername(username)) {
            throw new UsernameDuplicadoException(username);
        }
    }
}
