package com.horchata.sv.repository;

import com.horchata.sv.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByActivoTrue();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre = 'ADMIN'")
    List<Usuario> findAdministradores();

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre = 'USER'")
    List<Usuario> findClientes();
}
