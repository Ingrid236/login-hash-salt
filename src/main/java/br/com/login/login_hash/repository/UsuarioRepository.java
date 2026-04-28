package br.com.login.login_hash.repository;

import br.com.login.login_hash.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de acesso a dados para a entidade Usuario.
 * Responsabilidade única: fornecer operações de persistência para Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo email.
     *
     * @param email email do usuário
     * @return Optional contendo o usuário, se encontrado
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se já existe um usuário com o email informado.
     *
     * @param email email a verificar
     * @return true se o email já está cadastrado
     */
    boolean existsByEmail(String email);
}
