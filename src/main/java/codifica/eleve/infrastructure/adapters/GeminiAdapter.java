package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.application.ports.out.IdentificarRacaIAPort;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeminiAdapter implements IdentificarRacaIAPort {

    @Value("${GEMINI_API_KEY}")
    private String geminiApiKey;

    @Override
    public String identificarRaca(byte[] imageBytes, String mimeType) {
        try {
            Client client = Client.builder()
                    .apiKey(geminiApiKey)
                    .build();

            Part instrucaoDeTexto = Part.fromText(
                    "Analise a imagem e identifique a raça do cachorro. Se encontrar, sugira no máximo 3 nomes de raças prováveis retornando APENAS um JSON neste formato exato: [{\"raca\": \"Nome\"}]. Se não tiver certeza, retorne {\"raca\": \"Indefinida\"}. Não inclua formatação markdown, crases ou texto adicional."
            );
            Part imagemRecebida = Part.fromBytes(imageBytes, mimeType);

            Content content = Content.fromParts(instrucaoDeTexto, imagemRecebida);

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3-flash-preview",
                    content,
                    null
            );

            if (response != null && response.text() != null) {
                String textoResposta = response.text();
                return textoResposta.replace("```json", "").replace("```", "").trim();
            }

            return "{\"raca\": \"Indefinida\"}";
        } catch (Exception e) {
            throw new RuntimeException("Erro na integração com Gemini SDK: " + e.getMessage(), e);
        }
    }
}
