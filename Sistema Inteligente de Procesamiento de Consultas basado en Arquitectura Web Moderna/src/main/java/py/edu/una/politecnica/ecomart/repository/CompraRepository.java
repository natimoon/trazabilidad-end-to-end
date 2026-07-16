package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.Compra;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByClienteId(Long clienteId);

    List<Compra> findByProductoIdIn(List<Long> productoIds);

    @Query("SELECT DISTINCT c.clienteId FROM Compra c WHERE c.productoId IN :productoIds")
    List<Long> findClienteIdsByProductoIdIn(@Param("productoIds") List<Long> productoIds);
}
