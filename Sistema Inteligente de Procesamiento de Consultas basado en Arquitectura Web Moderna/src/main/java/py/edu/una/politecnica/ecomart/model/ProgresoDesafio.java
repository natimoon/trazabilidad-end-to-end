package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProgresoDesafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private Long desafioId;
    private Integer progreso = 0;
    private Boolean completado = false;
    private Boolean recompensaRecibida = false;
    private LocalDateTime fechaCompletado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getDesafioId() { return desafioId; }
    public void setDesafioId(Long desafioId) { this.desafioId = desafioId; }
    public Integer getProgreso() { return progreso; }
    public void setProgreso(Integer progreso) { this.progreso = progreso; }
    public Boolean getCompletado() { return completado; }
    public void setCompletado(Boolean completado) { this.completado = completado; }
    public Boolean getRecompensaRecibida() { return recompensaRecibida; }
    public void setRecompensaRecibida(Boolean recompensaRecibida) { this.recompensaRecibida = recompensaRecibida; }
    public LocalDateTime getFechaCompletado() { return fechaCompletado; }
    public void setFechaCompletado(LocalDateTime fechaCompletado) { this.fechaCompletado = fechaCompletado; }
}
