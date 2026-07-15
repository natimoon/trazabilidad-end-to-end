package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.EncuestaSatisfaccion;
import py.edu.una.politecnica.ecomart.service.EncuestaService;
import java.util.List;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    private final EncuestaService service;

    public EncuestaController(EncuestaService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<EncuestaSatisfaccion>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<EncuestaSatisfaccion>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<EncuestaSatisfaccion> crear(@RequestBody EncuestaSatisfaccion encuesta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(encuesta));
    }
}
