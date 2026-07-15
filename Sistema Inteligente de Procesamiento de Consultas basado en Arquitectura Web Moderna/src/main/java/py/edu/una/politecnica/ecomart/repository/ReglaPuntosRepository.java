package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.ReglaPuntos;
import java.util.Optional;

@Repository
public interface ReglaPuntosRepository extends JpaRepository<ReglaPuntos, Long> {

    Optional<ReglaPuntos> findByLimiteInferiorLessThanEqualAndLimiteSuperiorGreaterThanEqual(
            Double monto, Double monto2);
}
