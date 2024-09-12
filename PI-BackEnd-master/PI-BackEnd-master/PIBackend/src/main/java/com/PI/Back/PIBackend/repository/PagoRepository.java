package com.PI.Back.PIBackend.repository;

import com.PI.Back.PIBackend.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago>findByReferenciaTransaccion(String referenciaTransaccion);
}
