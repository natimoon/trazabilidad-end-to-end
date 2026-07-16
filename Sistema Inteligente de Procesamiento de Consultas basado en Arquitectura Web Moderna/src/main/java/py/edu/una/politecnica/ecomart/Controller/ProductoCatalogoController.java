package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.ProductoCatalogo;
import py.edu.una.politecnica.ecomart.service.ProductoCatalogoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos-canje")
public class ProductoCatalogoController {

    private final ProductoCatalogoService service;

    public ProductoCatalogoController(ProductoCatalogoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ProductoCatalogo>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoCatalogo>> listarDisponibles() {
        return ResponseEntity.ok(service.listarDisponibles());
    }

    @PostMapping("/sincronizar")
    public ResponseEntity<Map<String, Object>> sincronizar() {
        int agregados = service.sincronizarDesdeProductos();
        Map<String, Object> res = new HashMap<>();
        res.put("agregados", agregados);
        res.put("mensaje", agregados + " producto(s) sincronizado(s) al catálogo de canje");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoCatalogo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoCatalogo> crear(@RequestBody ProductoCatalogo producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoCatalogo> actualizar(@PathVariable Long id, @RequestBody ProductoCatalogo producto) {
        return ResponseEntity.ok(service.actualizar(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
