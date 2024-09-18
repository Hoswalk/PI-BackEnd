package com.PI.Back.PIBackend.repository;

import com.PI.Back.PIBackend.entity.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {

    List<Instrumento> findByNombre(String nombre);

    List<Instrumento> findByCategoria(String categoria);
}
