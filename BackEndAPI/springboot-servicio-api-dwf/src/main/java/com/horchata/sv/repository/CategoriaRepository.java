package com.horchata.sv.repository;

import com.horchata.sv.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
    List<Categoria> findByActivoTrue();

    @Query("SELECT c FROM Categoria c WHERE c.activo = true ORDER BY c.nombre")
    List<Categoria> findAllActivasOrdenadas();
}
