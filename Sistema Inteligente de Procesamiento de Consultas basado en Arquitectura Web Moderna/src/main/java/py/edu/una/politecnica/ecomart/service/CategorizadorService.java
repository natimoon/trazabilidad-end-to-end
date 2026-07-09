package py.edu.una.politecnica.ecomart.service;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.stereotype.Service;

@Service
public class CategorizadorService {

    private final ChatClient chatClient;

    public CategorizadorService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder
                        .builder()
                        .withModel("gpt-4o")
                        .build())
                .build();
    }

    public String categorizarProducto(String producto) {
        var system = """
                Actúa como un categorizador de productos y debes responder solo el nombre de la categoría del producto informado
                                
                Escoge una categoría de la siguiente lista:
                                
                1. Higiene Personal
                2. Electrónicos
                3. Deportes
                4. Prendas de vestir
                5. Otros
                                
                Ejemplo de uso:
                                
                Producto: Pelota de fútbol
                Respuesta: Deportes
                """;
        var tokens = contadorDeTokens(system, producto);
        System.out.println(tokens);

        return chatClient.prompt()
                .system(system)
                .user(producto)
                .options(ChatOptionsBuilder.builder()
                        .withTemperature(0.82f).build())
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

    private int contadorDeTokens(String system, String user) {
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);
        return enc.countTokens(system + user);
    }
}
