package py.edu.una.politecnica.ecomart.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeneradorService {

    private final ChatClient chatClient;

    public GeneradorService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generarProductos() {
        var pregunta = "Genera 5 productos ecológicos";
        return chatClient.prompt()
                .user(pregunta)
                .call()
                .content();
    }
}
