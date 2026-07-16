package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.Promocion;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {

    @Query("SELECT p FROM Promocion p WHERE p.activo = true AND p.fechaInicio <= :hoy AND p.fechaFin >= :hoy")
    List<Promocion> findActivas(@Param("hoy") LocalDate hoy);

    @Query("SELECT p FROM Promocion p WHERE p.activo = true AND p.fechaInicio <= :hoy AND p.fechaFin >= :hoy AND (p.categoria IS NULL OR p.categoria = :categoria)")
    List<Promocion> findActivasPorCategoria(@Param("hoy") LocalDate hoy, @Param("categoria") String categoria);
}
