package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.InsigniaCliente;
import java.util.List;

@Repository
public interface InsigniaClienteRepository extends JpaRepository<InsigniaCliente, Long> {
    List<InsigniaCliente> findByClienteId(Long clienteId);
    boolean existsByClienteIdAndInsigniaId(Long clienteId, Long insigniaId);
}
