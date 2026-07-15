package py.edu.una.politecnica.ecomart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReglaPuntos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double limiteInferior;
    private Double limiteSuperior;
    private Double montoEquivalencia;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getLimiteInferior() { return limiteInferior; }
    public void setLimiteInferior(Double limiteInferior) { this.limiteInferior = limiteInferior; }
    public Double getLimiteSuperior() { return limiteSuperior; }
    public void setLimiteSuperior(Double limiteSuperior) { this.limiteSuperior = limiteSuperior; }
    public Double getMontoEquivalencia() { return montoEquivalencia; }
    public void setMontoEquivalencia(Double montoEquivalencia) { this.montoEquivalencia = montoEquivalencia; }
}
