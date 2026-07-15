package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.ConceptoUso;

@Repository
public interface ConceptoUsoRepository extends JpaRepository<ConceptoUso, Long> {
}
