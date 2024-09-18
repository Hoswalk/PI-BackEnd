package com.PI.Back.PIBackend.repository;

import com.PI.Back.PIBackend.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository  extends JpaRepository<Caracteristica, Long> {
}
