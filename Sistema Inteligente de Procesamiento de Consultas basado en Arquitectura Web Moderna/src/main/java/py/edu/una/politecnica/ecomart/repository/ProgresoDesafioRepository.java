package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.ProgresoDesafio;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgresoDesafioRepository extends JpaRepository<ProgresoDesafio, Long> {
    List<ProgresoDesafio> findByClienteId(Long clienteId);
    Optional<ProgresoDesafio> findByClienteIdAndDesafioId(Long clienteId, Long desafioId);
}
