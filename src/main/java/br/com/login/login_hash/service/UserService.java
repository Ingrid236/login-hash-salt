package br.com.login.login_hash.service;

import br.com.login.login_hash.dto.UpdatePasswordRequestDTO;
import br.com.login.login_hash.dto.UserProfileResponseDTO;
import br.com.login.login_hash.dto.UserUpdateRequestDTO;
import br.com.login.login_hash.entity.Usuario;
import br.com.login.login_hash.exception.CredenciaisInvalidasException;
import br.com.login.login_hash.exception.EmailDuplicadoException;
import br.com.login.login_hash.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço para gerenciar as operações de perfil do usuário.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retorna o perfil do usuário atualmente autenticado.
     */
    public UserProfileResponseDTO getLoggedUserProfile() {
        Usuario usuario = getLoggedUser();
        return mapToResponseDTO(usuario);
    }

    /**
     * Atualiza a senha do usuário logado.
     */
    @Transactional
    public void updatePassword(UpdatePasswordRequestDTO request) {
        Usuario usuario = getLoggedUser();

        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenhaHash())) {
            throw new CredenciaisInvalidasException();
        }

        usuario.setSenhaHash(passwordEncoder.encode(request.getNovaSenha()));
        usuarioRepository.save(usuario);
    }

    /**
     * Atualiza os dados de perfil (nome e email) do usuário logado.
     */
    @Transactional
    public UserProfileResponseDTO updateUserProfile(UserUpdateRequestDTO request) {
        Usuario usuario = getLoggedUser();

        // Validar unicidade do email se estiver sendo alterado
        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail()) &&
                usuarioRepository.existsByEmailAndIdNot(request.getEmail(), usuario.getId())) {
            throw new EmailDuplicadoException("Este e-mail já está sendo utilizado por outro usuário.");
        }

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return mapToResponseDTO(usuarioAtualizado);
    }

    @Transactional
    public void deleteLoggedUser() {
        Usuario usuario = getLoggedUser();
        usuario.setActive(false);
        usuarioRepository.save(usuario);
    }

    /**
     * Recupera a entidade Usuario do banco de dados baseada no contexto de segurança.
     */
    public Usuario getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    private UserProfileResponseDTO mapToResponseDTO(Usuario usuario) {
        return new UserProfileResponseDTO(
                usuario.getNome(),
                usuario.getUsername(),
                usuario.getEmail()
        );
    }
}
