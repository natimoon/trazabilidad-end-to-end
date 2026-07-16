package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.Insignia;

@Repository
public interface InsigniaRepository extends JpaRepository<Insignia, Long> {
}
