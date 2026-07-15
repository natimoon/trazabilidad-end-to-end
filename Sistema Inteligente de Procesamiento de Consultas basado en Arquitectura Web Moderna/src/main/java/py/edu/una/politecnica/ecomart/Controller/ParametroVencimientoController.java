package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.ParametroVencimiento;
import py.edu.una.politecnica.ecomart.service.ParametroVencimientoService;
import java.util.List;

@RestController
@RequestMapping("/api/vencimientos")
public class ParametroVencimientoController {

    private final ParametroVencimientoService service;

    public ParametroVencimientoController(ParametroVencimientoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ParametroVencimiento>> listarTodos() { return ResponseEntity.ok(service.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<ParametroVencimiento> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ParametroVencimiento> crear(@RequestBody ParametroVencimiento param) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(param));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParametroVencimiento> actualizar(@PathVariable Long id, @RequestBody ParametroVencimiento param) {
        return ResponseEntity.ok(service.actualizar(id, param));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
