package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.ParametroVencimiento;
import py.edu.una.politecnica.ecomart.repository.ParametroVencimientoRepository;
import java.util.List;

@Service
public class ParametroVencimientoService {

    private final ParametroVencimientoRepository repository;

    public ParametroVencimientoService(ParametroVencimientoRepository repository) {
        this.repository = repository;
    }

    public List<ParametroVencimiento> listarTodos() { return repository.findAll(); }

    public ParametroVencimiento obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parámetro de vencimiento no encontrado con id: " + id));
    }

    public ParametroVencimiento crear(ParametroVencimiento param) { return repository.save(param); }

    public ParametroVencimiento actualizar(Long id, ParametroVencimiento actualizado) {
        ParametroVencimiento param = obtenerPorId(id);
        param.setFechaInicioValidez(actualizado.getFechaInicioValidez());
        param.setFechaFinValidez(actualizado.getFechaFinValidez());
        param.setDiasDuracion(actualizado.getDiasDuracion());
        return repository.save(param);
    }

    public void eliminar(Long id) {
        ParametroVencimiento param = obtenerPorId(id);
        repository.delete(param);
    }
}
