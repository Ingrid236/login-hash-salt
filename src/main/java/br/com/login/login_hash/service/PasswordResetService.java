package br.com.login.login_hash.service;

import br.com.login.login_hash.entity.Usuario;
import br.com.login.login_hash.entity.VerificationToken;
import br.com.login.login_hash.repository.UsuarioRepository;
import br.com.login.login_hash.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Transactional
    public void generateResetToken(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return; // Silently return to prevent email enumeration
        }

        Usuario usuario = usuarioOpt.get();
        String tokenStr = UUID.randomUUID().toString();
        
        VerificationToken token = new VerificationToken(
                tokenStr, 
                usuario, 
                LocalDateTime.now().plusHours(1)
        );
        tokenRepository.save(token);

        sendResetEmail(usuario.getEmail(), tokenStr);
    }

    private void sendResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, use the following token: " + token);
        mailSender.send(message);
    }

    @Transactional
    public void resetPassword(String tokenStr, String newPassword) {
        VerificationToken token = tokenRepository.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Token inválido ou expirado."));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(token);
            throw new RuntimeException("Token expirado.");
        }

        Usuario usuario = token.getUsuario();
        usuario.setSenhaHash(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);

        // Invalidate token
        tokenRepository.delete(token);
    }
}
