package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class BolsaPuntos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private LocalDateTime fechaAsignacion;
    private LocalDate fechaCaducidad;
    private Integer puntajeAsignado;
    private Integer puntajeUtilizado;
    private Integer saldoPuntos;
    private Double montoOperacion;
    private Boolean vencida;

    public BolsaPuntos() {
        this.puntajeUtilizado = 0;
        this.vencida = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
    public Integer getPuntajeAsignado() { return puntajeAsignado; }
    public void setPuntajeAsignado(Integer puntajeAsignado) { this.puntajeAsignado = puntajeAsignado; }
    public Integer getPuntajeUtilizado() { return puntajeUtilizado; }
    public void setPuntajeUtilizado(Integer puntajeUtilizado) { this.puntajeUtilizado = puntajeUtilizado; }
    public Integer getSaldoPuntos() { return saldoPuntos; }
    public void setSaldoPuntos(Integer saldoPuntos) { this.saldoPuntos = saldoPuntos; }
    public Double getMontoOperacion() { return montoOperacion; }
    public void setMontoOperacion(Double montoOperacion) { this.montoOperacion = montoOperacion; }
    public Boolean getVencida() { return vencida; }
    public void setVencida(Boolean vencida) { this.vencida = vencida; }
}
