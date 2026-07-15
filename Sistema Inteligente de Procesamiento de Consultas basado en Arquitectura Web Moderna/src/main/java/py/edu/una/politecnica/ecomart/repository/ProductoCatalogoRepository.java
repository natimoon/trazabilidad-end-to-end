package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.ProductoCatalogo;
import java.util.List;

@Repository
public interface ProductoCatalogoRepository extends JpaRepository<ProductoCatalogo, Long> {

    List<ProductoCatalogo> findByStockGreaterThan(Integer stock);
}
