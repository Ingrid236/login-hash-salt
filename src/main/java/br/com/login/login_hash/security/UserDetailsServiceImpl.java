package br.com.login.login_hash.security;

import br.com.login.login_hash.entity.Usuario;
import br.com.login.login_hash.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Identifier can be either email or username
        Usuario usuario = usuarioRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado com o identificador: " + identifier));

        return new User(
                usuario.getUsername(),
                usuario.getSenhaHash(),
                usuario.isActive(),
                true,
                true,
                true,
                new ArrayList<>() // No roles for MVP
        );
    }
}
