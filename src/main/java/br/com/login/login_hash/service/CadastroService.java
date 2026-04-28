package br.com.login.login_hash.service;

import br.com.login.login_hash.dto.CadastroRequestDTO;
import br.com.login.login_hash.entity.Usuario;
import br.com.login.login_hash.exception.EmailDuplicadoException;
import br.com.login.login_hash.repository.UsuarioRepository;
import br.com.login.login_hash.security.PasswordHasher;
import br.com.login.login_hash.security.SaltGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pelo cadastro de novos usuários.
 * Responsabilidade única: orquestrar o fluxo de cadastro
 * (validação de duplicidade, geração de salt, hashing e persistência).
 */
@Service
@RequiredArgsConstructor
public class CadastroService {

    private final UsuarioRepository usuarioRepository;
    private final SaltGenerator saltGenerator;
    private final PasswordHasher passwordHasher;

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param request dados de cadastro validados
     * @return o usuário persistido
     * @throws EmailDuplicadoException se o email já estiver cadastrado
     */
    @Transactional
    public Usuario cadastrar(CadastroRequestDTO request) {
        validarEmailUnico(request.getEmail());

        String salt = saltGenerator.gerarSalt();
        String senhaHash = passwordHasher.gerarHash(request.getSenha(), salt);

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(senhaHash);
        usuario.setSalt(salt);

        return usuarioRepository.save(usuario);
    }

    /**
     * Valida se o email já está em uso.
     *
     * @param email email a ser verificado
     * @throws EmailDuplicadoException se o email já existir
     */
    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailDuplicadoException(email);
        }
    }
}
