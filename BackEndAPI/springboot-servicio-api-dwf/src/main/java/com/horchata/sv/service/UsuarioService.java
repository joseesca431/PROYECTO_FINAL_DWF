package com.horchata.sv.service;

import com.horchata.sv.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario, String rolNombre);

    List<Usuario> listarUsuarios();

    Optional<Usuario> buscarPorUsername(String username);

    Optional<Usuario> buscarPorEmail(String email);

    boolean existeUsername(String username);

    boolean existeEmail(String email);

    Usuario actualizarUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    Optional<Usuario> buscarPorId(Long id);
}
