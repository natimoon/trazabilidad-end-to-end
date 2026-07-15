package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.ConceptoUso;
import py.edu.una.politecnica.ecomart.repository.ConceptoUsoRepository;
import java.util.List;

@Service
public class ConceptoUsoService {

    private final ConceptoUsoRepository repository;

    public ConceptoUsoService(ConceptoUsoRepository repository) {
        this.repository = repository;
    }

    public List<ConceptoUso> listarTodos() { return repository.findAll(); }

    public ConceptoUso obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concepto de uso no encontrado con id: " + id));
    }

    public ConceptoUso crear(ConceptoUso concepto) { return repository.save(concepto); }

    public ConceptoUso actualizar(Long id, ConceptoUso actualizado) {
        ConceptoUso concepto = obtenerPorId(id);
        concepto.setDescripcion(actualizado.getDescripcion());
        concepto.setPuntosRequeridos(actualizado.getPuntosRequeridos());
        return repository.save(concepto);
    }

    public void eliminar(Long id) {
        ConceptoUso concepto = obtenerPorId(id);
        repository.delete(concepto);
    }
}
