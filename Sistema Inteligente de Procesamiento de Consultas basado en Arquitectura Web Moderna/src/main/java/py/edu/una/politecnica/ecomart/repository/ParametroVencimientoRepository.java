package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.ParametroVencimiento;
import java.util.Optional;

@Repository
public interface ParametroVencimientoRepository extends JpaRepository<ParametroVencimiento, Long> {

    Optional<ParametroVencimiento> findTopByOrderByFechaFinValidezDesc();
}
