package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class ParametroVencimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInicioValidez;
    private LocalDate fechaFinValidez;
    private Integer diasDuracion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFechaInicioValidez() { return fechaInicioValidez; }
    public void setFechaInicioValidez(LocalDate fechaInicioValidez) { this.fechaInicioValidez = fechaInicioValidez; }
    public LocalDate getFechaFinValidez() { return fechaFinValidez; }
    public void setFechaFinValidez(LocalDate fechaFinValidez) { this.fechaFinValidez = fechaFinValidez; }
    public Integer getDiasDuracion() { return diasDuracion; }
    public void setDiasDuracion(Integer diasDuracion) { this.diasDuracion = diasDuracion; }
}
