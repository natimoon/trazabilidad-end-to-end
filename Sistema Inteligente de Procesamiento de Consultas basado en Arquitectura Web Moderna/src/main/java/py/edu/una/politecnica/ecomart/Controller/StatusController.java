package py.edu.una.politecnica.ecomart.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${spring.application.name:ecomart}")
    private String appName;

    @GetMapping("/instancia")
    public ResponseEntity<Map<String, String>> instancia() {
        return ResponseEntity.ok(Map.of(
            "instancia", "Servidor-" + serverPort,
            "puerto", serverPort,
            "aplicacion", appName,
            "estado", "activo"
        ));
    }

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "aplicacion", appName,
            "puerto", serverPort,
            "timestamp", System.currentTimeMillis()
        ));
    }
}
