package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.CanjePuntos;
import java.util.List;

@Repository
public interface CanjePuntosRepository extends JpaRepository<CanjePuntos, Long> {

    List<CanjePuntos> findByClienteId(Long clienteId);
}
