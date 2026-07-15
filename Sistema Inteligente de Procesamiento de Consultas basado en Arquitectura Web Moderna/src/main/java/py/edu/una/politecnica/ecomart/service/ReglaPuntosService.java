package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.ReglaPuntos;
import py.edu.una.politecnica.ecomart.repository.ReglaPuntosRepository;
import java.util.List;

@Service
public class ReglaPuntosService {

    private final ReglaPuntosRepository repository;

    public ReglaPuntosService(ReglaPuntosRepository repository) {
        this.repository = repository;
    }

    public List<ReglaPuntos> listarTodos() { return repository.findAll(); }

    public ReglaPuntos obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regla de puntos no encontrada con id: " + id));
    }

    public ReglaPuntos crear(ReglaPuntos regla) { return repository.save(regla); }

    public ReglaPuntos actualizar(Long id, ReglaPuntos actualizado) {
        ReglaPuntos regla = obtenerPorId(id);
        regla.setLimiteInferior(actualizado.getLimiteInferior());
        regla.setLimiteSuperior(actualizado.getLimiteSuperior());
        regla.setMontoEquivalencia(actualizado.getMontoEquivalencia());
        return repository.save(regla);
    }

    public void eliminar(Long id) {
        ReglaPuntos regla = obtenerPorId(id);
        repository.delete(regla);
    }

    public Integer calcularPuntosPorMonto(Double monto) {
        List<ReglaPuntos> reglas = repository.findAll();
        if (reglas.isEmpty()) {
            throw new RuntimeException("No hay reglas de puntos configuradas");
        }

        for (ReglaPuntos regla : reglas) {
            if (regla.getLimiteInferior() == null && regla.getLimiteSuperior() == null) {
                return (int) Math.floor(monto / regla.getMontoEquivalencia());
            }
            if (regla.getLimiteInferior() != null && regla.getLimiteSuperior() != null) {
                if (monto >= regla.getLimiteInferior() && monto <= regla.getLimiteSuperior()) {
                    return (int) Math.floor(monto / regla.getMontoEquivalencia());
                }
            } else if (regla.getLimiteInferior() != null && monto >= regla.getLimiteInferior()) {
                return (int) Math.floor(monto / regla.getMontoEquivalencia());
            }
        }

        ReglaPuntos ultima = reglas.get(reglas.size() - 1);
        return (int) Math.floor(monto / ultima.getMontoEquivalencia());
    }
}
