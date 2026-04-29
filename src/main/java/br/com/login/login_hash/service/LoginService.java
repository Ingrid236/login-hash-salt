package br.com.login.login_hash.service;

import br.com.login.login_hash.dto.LoginRequestDTO;
import br.com.login.login_hash.exception.CredenciaisInvalidasException;
import br.com.login.login_hash.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela autenticação de usuários.
 * Responsabilidade única: orquestrar o fluxo de login
 * e retornar o token JWT.
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Realiza a autenticação do usuário e gera o token.
     *
     * @param request dados de login (identifier e senha)
     * @return o token JWT gerado
     */
    public String autenticar(LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getSenha())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtService.generateToken(userDetails);
            
        } catch (Exception e) {
            throw new CredenciaisInvalidasException();
        }
    }
}
