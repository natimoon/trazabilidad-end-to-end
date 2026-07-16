package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.Compra;
import py.edu.una.politecnica.ecomart.service.CompraService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> comprar(@RequestBody Map<String, Long> body) {
        Long clienteId = body.get("clienteId");
        Long productoId = body.get("productoId");
        if (clienteId == null || productoId == null) {
            return ResponseEntity.badRequest().build();
        }
        Map<String, Object> resultado = compraService.comprar(clienteId, productoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping
    public ResponseEntity<List<Compra>> listarTodas() {
        return ResponseEntity.ok(compraService.listarTodas());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Compra>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(compraService.listarPorCliente(clienteId));
    }
}
