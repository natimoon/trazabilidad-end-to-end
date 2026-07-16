package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.Promocion;
import py.edu.una.politecnica.ecomart.service.PromocionService;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    private final PromocionService service;

    public PromocionController(PromocionService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Promocion>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promocion> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Promocion> crear(@RequestBody Promocion promocion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(promocion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promocion> actualizar(@PathVariable Long id, @RequestBody Promocion promocion) {
        return ResponseEntity.ok(service.actualizar(id, promocion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
