package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.Desafio;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DesafioRepository extends JpaRepository<Desafio, Long> {
    @Query("SELECT d FROM Desafio d WHERE d.activo = true AND d.fechaInicio <= :hoy AND d.fechaFin >= :hoy")
    List<Desafio> findActivos(@Param("hoy") LocalDate hoy);
}
