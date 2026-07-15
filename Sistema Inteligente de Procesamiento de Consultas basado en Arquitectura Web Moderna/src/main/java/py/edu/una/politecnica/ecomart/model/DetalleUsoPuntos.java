package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DetalleUsoPuntos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cabeceraId;
    private Integer puntajeUtilizado;
    private Long bolsaPuntosId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCabeceraId() { return cabeceraId; }
    public void setCabeceraId(Long cabeceraId) { this.cabeceraId = cabeceraId; }
    public Integer getPuntajeUtilizado() { return puntajeUtilizado; }
    public void setPuntajeUtilizado(Integer puntajeUtilizado) { this.puntajeUtilizado = puntajeUtilizado; }
    public Long getBolsaPuntosId() { return bolsaPuntosId; }
    public void setBolsaPuntosId(Long bolsaPuntosId) { this.bolsaPuntosId = bolsaPuntosId; }
}
