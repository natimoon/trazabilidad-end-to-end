package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class InsigniaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;
    private Long insigniaId;
    private LocalDateTime fechaObtenida;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getInsigniaId() { return insigniaId; }
    public void setInsigniaId(Long insigniaId) { this.insigniaId = insigniaId; }
    public LocalDateTime getFechaObtenida() { return fechaObtenida; }
    public void setFechaObtenida(LocalDateTime fechaObtenida) { this.fechaObtenida = fechaObtenida; }
}
