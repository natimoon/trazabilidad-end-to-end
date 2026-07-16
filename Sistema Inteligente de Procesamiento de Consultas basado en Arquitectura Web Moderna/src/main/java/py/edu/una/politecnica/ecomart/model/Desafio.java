package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Desafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String tipo;
    private Integer objetivo;
    private String categoria;
    private Integer puntosRecompensa;
    private String insigniaRecompensa;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getObjetivo() { return objetivo; }
    public void setObjetivo(Integer objetivo) { this.objetivo = objetivo; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getPuntosRecompensa() { return puntosRecompensa; }
    public void setPuntosRecompensa(Integer puntosRecompensa) { this.puntosRecompensa = puntosRecompensa; }
    public String getInsigniaRecompensa() { return insigniaRecompensa; }
    public void setInsigniaRecompensa(String insigniaRecompensa) { this.insigniaRecompensa = insigniaRecompensa; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
