package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.pet.FindPetsByClienteIdUseCase;
import codifica.eleve.interfaces.dtoAdapters.PetDtoMapper;
import codifica.eleve.interfaces.dto.PetDTO;
import codifica.eleve.infrastructure.adapters.RacaExternaAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final FindPetsByClienteIdUseCase findPetsByClienteIdUseCase;
    private final PetDtoMapper petDtoMapper;
    private final RacaExternaAdapter racaExternaAdapter;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public DashboardController(FindPetsByClienteIdUseCase findPetsByClienteIdUseCase,
                               PetDtoMapper petDtoMapper,
                               RacaExternaAdapter racaExternaAdapter,
                               ResourceLoader resourceLoader,
                               ObjectMapper objectMapper) {
        this.findPetsByClienteIdUseCase = findPetsByClienteIdUseCase;
        this.petDtoMapper = petDtoMapper;
        this.racaExternaAdapter = racaExternaAdapter;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Map<String, Object>> obterResumoDashboard(@PathVariable Integer clienteId) {
        List<PetDTO> pets = carregarPetsDoCliente(clienteId);

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("pets", pets);
        resumo.put("analyticsResumo", obterAnalyticsResumoSeguro());
        resumo.put("totalPets", pets.size());
        resumo.put("insightsRaca", gerarInsightsRaca(pets));
        resumo.put("insightsAudio", gerarInsightsAudio(pets));

        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/cliente/{clienteId}/insights/raca")
    public ResponseEntity<List<Map<String, Object>>> obterInsightsRaca(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(gerarInsightsRaca(carregarPetsDoCliente(clienteId)));
    }

    @GetMapping("/cliente/{clienteId}/insights/audio")
    public ResponseEntity<List<Map<String, Object>>> obterInsightsAudio(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(gerarInsightsAudio(carregarPetsDoCliente(clienteId)));
    }

    private List<PetDTO> carregarPetsDoCliente(Integer clienteId) {
        return findPetsByClienteIdUseCase.execute(clienteId).stream()
                .map(petDtoMapper::toChatbotDto)
                .collect(Collectors.toList());
    }

    private Map<String, Object> obterAnalyticsResumoSeguro() {
        try {
            return racaExternaAdapter.obterResumoAnalytics();
        } catch (Exception e) {
            return new LinkedHashMap<>();
        }
    }

    private List<Map<String, Object>> gerarInsightsRaca(List<PetDTO> pets) {
        List<Map<String, Object>> insights = new ArrayList<>();
        Instant now = Instant.now();

        for (int index = 0; index < pets.size(); index++) {
            PetDTO pet = pets.get(index);
            Map<String, Object> insight = new LinkedHashMap<>();
            String nomeRaca = pet.getRaca() != null ? pet.getRaca().getNome() : "Indefinida";
            String porte = pet.getPorte() != null ? pet.getPorte().getNome() : "—";

            insight.put("id", "insight-raca-" + (pet.getId() != null ? pet.getId() : index + 1));
            insight.put("createdAt", now.minus(index, ChronoUnit.DAYS).toString());
            insight.put("petId", pet.getId());
            insight.put("nomePet", pet.getNome());
            insight.put("nomeRaca", nomeRaca);
            insight.put("porte", porte);
            insight.put("infoRacaExterna", carregarInfoRacaSegura(nomeRaca));
            insight.put("sugestoesIA", List.of(Map.of("raca", nomeRaca, "probabilidade", 0.75)));
            insight.put("insightsByTopic", gerarTopicosDeInsight(nomeRaca, porte));

            insights.add(insight);
        }

        return insights;
    }

    private Map<String, Object> carregarInfoRacaSegura(String nomeRaca) {
        if (nomeRaca == null || nomeRaca.trim().isEmpty()) {
            return new HashMap<>();
        }
        try {
            return racaExternaAdapter.obterInfoRaca(nomeRaca);
        } catch (Exception e) {
            Map<String, Object> fallback = new HashMap<>();
            fallback.put("nome", nomeRaca);
            fallback.put("fonte", "não disponível");
            return fallback;
        }
    }

    private Map<String, Object> gerarTopicosDeInsight(String nomeRaca, String porte) {
        Map<String, Object> topicos = new LinkedHashMap<>();
        Map<String, Map<String, Object>> insightsPorRaca = obterInsightsPorRaca();
        String racaNormalizada = normalizarNomeRaca(nomeRaca);
        Map<String, Object> insightsRaca = insightsPorRaca.getOrDefault(racaNormalizada, new HashMap<>());

        // Saúde
        Map<String, Object> saudeData = (Map<String, Object>) insightsRaca.getOrDefault("saude", new HashMap<>());
        topicos.put("saude", Map.of(
                "nivelRisco", saudeData.getOrDefault("nivelRisco", "baixo"),
                "bullets", saudeData.getOrDefault("bullets", List.of(
                        "Mantenha consultas veterinárias regulares e rotina de vacinas em dia.",
                        "Observe sinais de coceira, vermelhidão ou alterações no apetite.",
                        "Hidratação e alimentação equilibrada são essenciais para saúde geral.")),
                "acoesSugeridas", List.of(
                        Map.of("tipo", "checklist", "label", "Checklist de saúde", "destino", "PerfilTab"),
                        Map.of("tipo", "agendar", "label", "Agendar avaliação", "destino", "AgendaTab"))
        ));

        // Banho
        Map<String, Object> banhoData = (Map<String, Object>) insightsRaca.getOrDefault("banho", new HashMap<>());
        topicos.put("banho", Map.of(
                "nivelRisco", banhoData.getOrDefault("nivelRisco", "baixo"),
                "bullets", banhoData.getOrDefault("bullets", List.of(
                        "Shampoo e secagem completos são importantes para evitar fungos e odor.",
                        "Escove antes do banho para reduzir nós e facilitar a aplicação do produto.",
                        "Cuidado especial nas dobrinhas e orelhas de raças braquicefálicas.")),
                "acoesSugeridas", List.of(Map.of("tipo", "agendar", "label", "Agendar banho", "destino", "AgendaTab"))
        ));

        // Comportamento
        Map<String, Object> comportamentoData = (Map<String, Object>) insightsRaca.getOrDefault("comportamento", new HashMap<>());
        topicos.put("comportamento", Map.of(
                "nivelRisco", comportamentoData.getOrDefault("nivelRisco", "baixo"),
                "bullets", comportamentoData.getOrDefault("bullets", List.of(
                        "Rotina previsível e reforço positivo ajudam no aprendizado.",
                        "Exercícios curtos e consistentes reduzem estresse e ansiedade.",
                        "Brinquedos interativos ajudam a distrair pets mais ativos.")),
                "acoesSugeridas", List.of(Map.of("tipo", "conteudo", "label", "Ver dicas de treino", "destino", "HistoricoTab"))
        ));

        // Alimentação
        Map<String, Object> alimentacaoData = (Map<String, Object>) insightsRaca.getOrDefault("alimentacao", new HashMap<>());
        topicos.put("alimentacao", Map.of(
                "nivelRisco", alimentacaoData.getOrDefault("nivelRisco", "baixo"),
                "bullets", alimentacaoData.getOrDefault("bullets", List.of(
                        "Ajuste porções de acordo com idade, peso e nível de atividade.",
                        "Mantenha água fresca sempre disponível.",
                        "Evite petiscos em excesso para controlar o peso.")),
                "acoesSugeridas", List.of(Map.of("tipo", "conteudo", "label", "Guia de porções", "destino", "HistoricoTab"))
        ));

        return topicos;
    }

    private String fecharPonto(String texto) {
        return texto + ".";
    }

    private String normalizarNomeRaca(String nomeRaca) {
        if (nomeRaca == null || nomeRaca.trim().isEmpty()) {
            return "";
        }
        return nomeRaca.toLowerCase()
                .replace(" ", "_")
                .replace("-", "_");
    }

    private Map<String, Map<String, Object>> obterInsightsPorRaca() {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:insights-raca.json").getInputStream();
            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            return new LinkedHashMap<>();
        }
    }


    private List<Map<String, Object>> gerarInsightsAudio(List<PetDTO> pets) {
        List<Map<String, Object>> audios = new ArrayList<>();
        Instant base = Instant.now();

        String raca = pets.stream()
                .map(pet -> pet.getRaca() != null ? pet.getRaca().getNome() : "")
                .filter(nome -> nome != null && !nome.isBlank())
                .findFirst()
                .orElse("cachorro");

        audios.add(criarAudioInsight(base, 1, "banho",
                String.format("Por que o %s precisa de cuidado especial após o banho?", raca),
                Map.of("raca", raca, "tema", List.of("pele", "banho", "dobrinhas")),
                "O banho deve ser seguido de secagem completa, atenção às dobrinhas e produtos suaves para evitar irritação.",
                List.of(Map.of("tipo", "checklist", "label", "Checklist pós-banho", "destino", "HistoricoTab"),
                        Map.of("tipo", "agendar", "label", "Agendar banho", "destino", "AgendaTab"))));

        audios.add(criarAudioInsight(base, 3, "saude",
                "Meu cachorro está coçando depois do banho. Pode ser alergia?",
                Map.of("tema", List.of("coceira", "pele", "alergia")),
                "Coceira pode ser causada por shampoo, pele seca ou alergia. Observe vermelhidão e procure um veterinário se persistir.",
                List.of(Map.of("tipo", "checklist", "label", "Checklist pele", "destino", "HistoricoTab"),
                        Map.of("tipo", "agendar", "label", "Agendar avaliação", "destino", "AgendaTab"))));

        audios.add(criarAudioInsight(base, 5, "alimentacao",
                "Quantas vezes por dia devo dar ração ao meu cachorro?",
                Map.of("tema", List.of("ração", "porção", "rotina")),
                "A maioria dos cães faz bem com duas refeições por dia; ajuste a porção conforme peso e atividade.",
                List.of(Map.of("tipo", "conteudo", "label", "Guia de porções", "destino", "HistoricoTab"))));

        if (!raca.toLowerCase().contains("pug")) {
            audios.add(criarAudioInsight(base, 10, "comportamento",
                    "Meu cachorro late quando fico fora. Isso é ansiedade?",
                    Map.of("tema", List.of("ansiedade", "latido", "sozinho")),
                    "Pode ser ansiedade de separação. Rotina previsível e brinquedos interativos ajudam a reduzir o estresse.",
                    List.of(Map.of("tipo", "conteudo", "label", "Plano anti-ansiedade", "destino", "HistoricoTab"))));
        }

        return audios;
    }

    private Map<String, Object> criarAudioInsight(Instant base, int diasAtras, String topicoKey,
                                                  String perguntaTranscrita, Map<String, Object> entidades,
                                                  String resposta, List<Map<String, Object>> acoesSugeridas) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", UUID.randomUUID().toString());
        item.put("createdAt", base.minus(diasAtras, ChronoUnit.DAYS).toString());
        item.put("origem", "gravacao");
        item.put("topicoKey", topicoKey);
        item.put("perguntaTranscrita", perguntaTranscrita);
        item.put("entidades", entidades);
        item.put("nivelRisco", topicoKey.equals("saude") || topicoKey.equals("alimentacao") ? "baixo" : "medio");
        item.put("confianca", 0.72);
        item.put("resposta", resposta);
        item.put("acoesSugeridas", acoesSugeridas);
        return item;
    }
}
