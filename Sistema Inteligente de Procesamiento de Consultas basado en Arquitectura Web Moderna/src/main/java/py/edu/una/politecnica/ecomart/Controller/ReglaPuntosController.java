package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.ReglaPuntos;
import py.edu.una.politecnica.ecomart.service.ReglaPuntosService;
import java.util.List;

@RestController
@RequestMapping("/api/reglas-puntos")
public class ReglaPuntosController {

    private final ReglaPuntosService service;

    public ReglaPuntosController(ReglaPuntosService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ReglaPuntos>> listarTodos() { return ResponseEntity.ok(service.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<ReglaPuntos> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReglaPuntos> crear(@RequestBody ReglaPuntos regla) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(regla));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReglaPuntos> actualizar(@PathVariable Long id, @RequestBody ReglaPuntos regla) {
        return ResponseEntity.ok(service.actualizar(id, regla));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
