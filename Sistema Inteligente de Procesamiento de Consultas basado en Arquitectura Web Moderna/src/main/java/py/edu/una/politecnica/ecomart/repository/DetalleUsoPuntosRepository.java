package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.DetalleUsoPuntos;
import java.util.List;

@Repository
public interface DetalleUsoPuntosRepository extends JpaRepository<DetalleUsoPuntos, Long> {

    List<DetalleUsoPuntos> findByCabeceraId(Long cabeceraId);
}
