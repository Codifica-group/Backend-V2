package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.application.ports.out.AvaliarCondicaoPetIAPort;
import codifica.eleve.core.application.ports.out.IdentificarRacaIAPort;
import codifica.eleve.core.application.ports.out.ProcessarAudioIAPort;
import codifica.eleve.core.domain.raca.Raca;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeminiAdapter implements IdentificarRacaIAPort, AvaliarCondicaoPetIAPort, ProcessarAudioIAPort {

    @Value("${GEMINI_API_KEY}")
    private String geminiApiKey;

    private static final Logger logger = LoggerFactory.getLogger(GeminiAdapter.class);

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

    @Override
    public String avaliarCondicao(byte[] imageBytes, String mimeType, String servicosSolicitados, Raca raca) {
        try {
            Client client = Client.builder()
                    .apiKey(geminiApiKey)
                    .build();

            String prompt = String.format(
                    "Analise a imagem deste %s e os serviços solicitados: %s. " +
                            "Avalie o estado do cachorro (sujeira, tamanho e nós no pelo). " +
                            "Retorne um JSON no formato exato: {\"multiplicador\": valor}, onde 'valor' varia " +
                            "de 1.0 (cachorro bem cuidado, sem adicional) a 2.0 (cachorro precisa de muito cuidado, " +
                            "dobro do valor). Não inclua formatação markdown ou texto adicional.",
                    raca.getNome(), servicosSolicitados
            );

            logger.info(prompt);

            Part instrucaoDeTexto = Part.fromText(prompt);
            Part imagemRecebida = Part.fromBytes(imageBytes, mimeType);

            Content content = Content.fromParts(instrucaoDeTexto, imagemRecebida);

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3-flash-preview",
                    content,
                    null
            );

            if (response != null && response.text() != null) {
                logger.info(response.text());
                return response.text().replace("```json", "").replace("```", "").trim();
            }

            return "{\"multiplicador\": 1.0}";
        } catch (Exception e) {
            throw new RuntimeException("Erro na integração com Gemini SDK: " + e.getMessage(), e);
        }
    }

    @Override
    public String processarAudio(byte[] audioBytes, String mimeType, String prompt) {
        try {
            Client client = Client.builder()
                    .apiKey(geminiApiKey)
                    .build();

            Part instrucaoDeTexto = Part.fromText(prompt);
            Part audioRecebido = Part.fromBytes(audioBytes, mimeType);

            Content content = Content.fromParts(instrucaoDeTexto, audioRecebido);

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3-flash-preview",
                    content,
                    null
            );

            if (response != null && response.text() != null) {
                return response.text().trim();
            }

            return "Não foi possível obter uma resposta para o áudio.";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar áudio no Gemini: " + e.getMessage(), e);
        }
    }
}
