package com.PI.Back.PIBackend.repository;

import com.PI.Back.PIBackend.entity.Alquiler;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    @Query("SELECT a FROM Alquiler a WHERE a.instrumento.id = :instrumentoId AND " +
            "(a.fechaInicio < :fechaFin AND a.fechaFin > :fechaInicio)")
    List<Alquiler> findConflictingAlquiler(@Param("instrumentoId") Long instrumentoId, @Param("fechaInicio")LocalDate fechaInicio, @Param("fechaFin")LocalDate fechaFin);
}
