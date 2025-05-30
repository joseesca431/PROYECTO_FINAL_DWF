package com.horchata.sv.repository;

import com.horchata.sv.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findByNombre(String nombre);
    List<Marca> findByActivoTrue();

    @Query("SELECT m FROM Marca m WHERE m.activo = true ORDER BY m.nombre")
    List<Marca> findAllActivasOrdenadas();
}
