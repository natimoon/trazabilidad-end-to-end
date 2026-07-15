package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.CabeceraUsoPuntos;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CabeceraUsoPuntosRepository extends JpaRepository<CabeceraUsoPuntos, Long> {

    List<CabeceraUsoPuntos> findByClienteId(Long clienteId);

    List<CabeceraUsoPuntos> findByConceptoUsoId(Long conceptoUsoId);

    List<CabeceraUsoPuntos> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<CabeceraUsoPuntos> findByClienteIdAndFechaBetween(Long clienteId, LocalDateTime inicio, LocalDateTime fin);
}
