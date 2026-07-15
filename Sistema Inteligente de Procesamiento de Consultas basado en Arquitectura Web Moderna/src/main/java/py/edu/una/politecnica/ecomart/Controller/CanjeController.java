package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.edu.una.politecnica.ecomart.model.CanjePuntos;
import py.edu.una.politecnica.ecomart.service.CanjeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/canje")
public class CanjeController {

    private final CanjeService canjeService;

    public CanjeController(CanjeService canjeService) { this.canjeService = canjeService; }

    @PostMapping
    public ResponseEntity<CanjePuntos> canjear(@RequestBody Map<String, Long> body) {
        Long clienteId = body.get("clienteId");
        Long productoId = body.get("productoId");
        return ResponseEntity.status(HttpStatus.CREATED).body(canjeService.canjear(clienteId, productoId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CanjePuntos>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(canjeService.listarPorCliente(clienteId));
    }

    @GetMapping
    public ResponseEntity<List<CanjePuntos>> listarTodos() {
        return ResponseEntity.ok(canjeService.listarTodos());
    }
}
