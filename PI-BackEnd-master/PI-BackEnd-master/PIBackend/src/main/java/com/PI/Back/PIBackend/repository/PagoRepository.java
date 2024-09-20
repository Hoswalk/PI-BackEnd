package com.PI.Back.PIBackend.repository;

import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.entity.Alquiler;
import com.PI.Back.PIBackend.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago>findByReferenciaTransaccion(String referenciaTransaccion);
    List<Pago> findAllByUsuarioId(Long id);

    Optional<Pago> findByAlquiler(Alquiler alquiler);
}
