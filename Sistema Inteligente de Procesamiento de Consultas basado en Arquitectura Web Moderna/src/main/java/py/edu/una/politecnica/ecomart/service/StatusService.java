package py.edu.una.politecnica.ecomart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class StatusService {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${spring.application.name:ecomart}")
    private String appName;

    public Map<String, String> getInstancia() {
        return Map.of(
            "instancia", "Servidor-" + serverPort,
            "puerto", serverPort,
            "aplicacion", appName,
            "estado", "activo"
        );
    }

    public Map<String, Object> getHealth() {
        return Map.of(
            "status", "UP",
            "aplicacion", appName,
            "puerto", serverPort,
            "timestamp", System.currentTimeMillis()
        );
    }
}
