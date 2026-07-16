package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.Cliente;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);

    @Query("SELECT c FROM Cliente c WHERE FUNCTION('MONTH', c.fechaNacimiento) = :mes AND FUNCTION('DAY', c.fechaNacimiento) = :dia")
    List<Cliente> findByCumpleanos(@Param("mes") int mes, @Param("dia") int dia);

    List<Cliente> findByNivel(String nivel);

    List<Cliente> findByReferidoPorId(Long referidoPorId);

    @Query("SELECT c FROM Cliente c WHERE c.puntosAcumulados BETWEEN :min AND :max")
    List<Cliente> findByRangoPuntos(@Param("min") int min, @Param("max") int max);

    List<Cliente> findByNacionalidad(String nacionalidad);

    List<Cliente> findByCiudadContainingIgnoreCase(String ciudad);
}
