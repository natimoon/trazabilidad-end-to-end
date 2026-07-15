package py.edu.una.politecnica.ecomart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.edu.una.politecnica.ecomart.model.BolsaPuntos;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BolsaPuntosRepository extends JpaRepository<BolsaPuntos, Long> {

    List<BolsaPuntos> findByClienteIdOrderByFechaAsignacionAsc(Long clienteId);

    @Query("SELECT b FROM BolsaPuntos b WHERE b.clienteId = :clienteId AND b.saldoPuntos > 0 AND (b.vencida IS NULL OR b.vencida = false) ORDER BY b.fechaAsignacion ASC")
    List<BolsaPuntos> findBolsasActivasPorCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT b FROM BolsaPuntos b WHERE b.fechaCaducidad <= :fecha AND (b.vencida IS NULL OR b.vencida = false)")
    List<BolsaPuntos> findBolsasAVencer(@Param("fecha") LocalDate fecha);

    @Query("SELECT b FROM BolsaPuntos b WHERE b.clienteId = :clienteId AND b.fechaCaducidad <= :fecha AND (b.vencida IS NULL OR b.vencida = false)")
    List<BolsaPuntos> findBolsasPorClienteAVencer(@Param("clienteId") Long clienteId, @Param("fecha") LocalDate fecha);

    @Query("SELECT b FROM BolsaPuntos b WHERE b.saldoPuntos BETWEEN :min AND :max")
    List<BolsaPuntos> findByRangoPuntos(@Param("min") Integer min, @Param("max") Integer max);

    List<BolsaPuntos> findByClienteId(Long clienteId);
}
