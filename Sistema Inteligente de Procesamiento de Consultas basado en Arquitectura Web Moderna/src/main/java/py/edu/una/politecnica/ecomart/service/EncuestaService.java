package py.edu.una.politecnica.ecomart.service;

import org.springframework.stereotype.Service;
import py.edu.una.politecnica.ecomart.model.EncuestaSatisfaccion;
import py.edu.una.politecnica.ecomart.repository.EncuestaSatisfaccionRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EncuestaService {

    private final EncuestaSatisfaccionRepository repository;

    public EncuestaService(EncuestaSatisfaccionRepository repository) {
        this.repository = repository;
    }

    public List<EncuestaSatisfaccion> listarTodas() { return repository.findAll(); }

    public List<EncuestaSatisfaccion> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public EncuestaSatisfaccion crear(EncuestaSatisfaccion encuesta) {
        encuesta.setFecha(LocalDateTime.now());
        return repository.save(encuesta);
    }
}
