package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class CabeceraUsoPuntos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private Integer puntajeUtilizado;
    private LocalDateTime fecha;
    private Long conceptoUsoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Integer getPuntajeUtilizado() { return puntajeUtilizado; }
    public void setPuntajeUtilizado(Integer puntajeUtilizado) { this.puntajeUtilizado = puntajeUtilizado; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Long getConceptoUsoId() { return conceptoUsoId; }
    public void setConceptoUsoId(Long conceptoUsoId) { this.conceptoUsoId = conceptoUsoId; }
}
