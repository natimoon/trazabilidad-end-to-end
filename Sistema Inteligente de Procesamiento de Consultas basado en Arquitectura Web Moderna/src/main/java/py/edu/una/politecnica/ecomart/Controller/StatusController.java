package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import py.edu.una.politecnica.ecomart.service.StatusService;

import java.util.Map;

@RestController
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/instancia")
    public ResponseEntity<Map<String, String>> instancia() {
        return ResponseEntity.ok(statusService.getInstancia());
    }

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(statusService.getHealth());
    }
}
