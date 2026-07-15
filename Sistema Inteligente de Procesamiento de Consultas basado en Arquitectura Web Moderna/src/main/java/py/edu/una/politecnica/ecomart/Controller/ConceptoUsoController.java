package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.ConceptoUso;
import py.edu.una.politecnica.ecomart.service.ConceptoUsoService;
import java.util.List;

@RestController
@RequestMapping("/api/conceptos-uso")
public class ConceptoUsoController {

    private final ConceptoUsoService service;

    public ConceptoUsoController(ConceptoUsoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ConceptoUso>> listarTodos() { return ResponseEntity.ok(service.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<ConceptoUso> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ConceptoUso> crear(@RequestBody ConceptoUso concepto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(concepto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConceptoUso> actualizar(@PathVariable Long id, @RequestBody ConceptoUso concepto) {
        return ResponseEntity.ok(service.actualizar(id, concepto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
