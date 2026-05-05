package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.shared.exceptions.InternalServerErrorException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.infrastructure.persistence.raca.RacaExternaEntity;
import codifica.eleve.infrastructure.persistence.raca.RacaExternaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class RacaExternaAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RacaExternaAdapter.class);
    private final RacaExternaRepository racaExternaRepository;

    @Value("${URL_DADOS_PY}")
    private String urlDadosPy;

    public RacaExternaAdapter(RacaExternaRepository racaExternaRepository) {
        this.racaExternaRepository = racaExternaRepository;
    }

    public Map<String, Object> obterInfoRaca(String nomeRaca) {
        String nomeLimpo = nomeRaca == null ? "" : nomeRaca.trim();
        if (nomeLimpo.length() < 2) {
            throw new codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException(
                    "Nome da raca deve ter pelo menos 2 caracteres."
            );
        }

        Optional<RacaExternaEntity> cacheLocal = racaExternaRepository
                .findFirstByNomeIgnoreCaseOrNomeOriginalIgnoreCase(nomeLimpo, nomeLimpo)
                .filter(raca -> raca.getAtivo() == null || Boolean.TRUE.equals(raca.getAtivo()));

        if (cacheLocal.isPresent()) {
            RacaExternaEntity raca = cacheLocal.get();
            logger.info(
                    "RACA_EXTERNA consulta concluida via banco local. nomeOriginal='{}', nomeExterno='{}', raceId='{}'",
                    nomeLimpo,
                    raca.getNomeOriginal() != null ? raca.getNomeOriginal() : raca.getNome(),
                    raca.getRacaIdExterno()
            );
            return mapearEntidadeParaResposta(nomeLimpo, raca);
        }

        URI uri = UriComponentsBuilder
                .fromUriString(urlDadosPy)
                .path("/racas/info")
                .queryParam("nome", nomeLimpo)
                .build()
                .encode()
                .toUri();

        logger.info("RACA_EXTERNA consulta iniciada. nomeOriginal='{}', uri='{}'", nomeLimpo, uri);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            Map<String, Object> corpo = response.getBody();

            if (corpo == null || corpo.isEmpty()) {
                logger.warn("RACA_EXTERNA resposta vazia. nomeOriginal='{}', uri='{}'", nomeLimpo, uri);
                throw new NotFoundException("Raca nao encontrada na base externa local.");
            }

            salvarOuAtualizarRacaExterna(nomeLimpo, corpo);

            logger.info(
                    "RACA_EXTERNA consulta concluida. nomeOriginal='{}', nomeExterno='{}', fonte='{}', raceId='{}'",
                    nomeLimpo,
                    corpo.getOrDefault("nomeExterno", corpo.get("nome")),
                    corpo.getOrDefault("fonte", "desconhecida"),
                    corpo.getOrDefault("raceId", "n/a")
            );
            return corpo;
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("RACA_EXTERNA nao encontrada. nomeOriginal='{}', uri='{}'", nomeLimpo, uri);
            throw new NotFoundException("Raca nao encontrada na base externa local.");
        } catch (HttpStatusCodeException e) {
            logger.error(
                    "RACA_EXTERNA falha HTTP. nomeOriginal='{}', uri='{}', status='{}', corpo='{}'",
                    nomeLimpo,
                    uri,
                    e.getStatusCode(),
                    e.getResponseBodyAsString(),
                    e
            );
            throw new InternalServerErrorException("Erro ao consultar dados externos de raca: " + e.getMessage());
        } catch (codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(
                    "RACA_EXTERNA falha inesperada. nomeOriginal='{}', uri='{}', detalhe='{}'",
                    nomeLimpo,
                    uri,
                    e.getMessage(),
                    e
            );
            throw new InternalServerErrorException("Erro ao consultar dados externos de raca: " + e.getMessage());
        }
    }

    public Map<String, Object> obterResumoAnalytics() {
        String url = urlDadosPy + "/analytics/resumo";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            Map<String, Object> corpo = response.getBody();

            if (corpo == null) {
                throw new InternalServerErrorException("Resposta vazia do servico de dados externos.");
            }

            return corpo;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao consultar analytics externos: " + e.getMessage());
        }
    }

    public void cadastrarRacaNoPython(String nome) {
        if (nome == null || nome.trim().length() < 2) return;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"nome\": \"" + nome.trim().replace("\"", "\\\"") + "\"}";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(
                urlDadosPy + "/racas/cadastrar",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
        } catch (Exception e) {
            logger.warn("Nao foi possivel pre-cachear raca '{}' no servico Python: {}", nome, e.getMessage());
        }
    }

    public Map<String, Object> sincronizarRacas() {
        String url = urlDadosPy + "/etl/sync/racas";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            Map<String, Object> corpo = response.getBody();

            if (corpo == null) {
                throw new InternalServerErrorException("Resposta vazia ao sincronizar racas externas.");
            }

            return corpo;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao sincronizar racas externas: " + e.getMessage());
        }
    }

    private Map<String, Object> mapearEntidadeParaResposta(String nomeConsultado, RacaExternaEntity raca) {
        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("nome", nomeConsultado);
        resposta.put("nomeExterno", valorOuPadrao(raca.getNomeOriginal(), raca.getNome()));
        resposta.put("grupo", valorOuPadrao(raca.getGrupo(), "Nao informado"));
        resposta.put("temperamento", valorOuPadrao(raca.getTemperamento(), "Nao informado"));
        resposta.put("expectativaVida", valorOuPadrao(raca.getVidaMedia(), "Nao informado"));
        resposta.put("peso", valorOuPadrao(raca.getPeso(), "Nao informado"));
        resposta.put("altura", valorOuPadrao(raca.getAltura(), "Nao informado"));
        resposta.put("origemRaca", valorOuPadrao(raca.getOrigem(), "Nao informado"));
        resposta.put("proposito", valorOuPadrao(raca.getProposito(), "Nao informado"));
        resposta.put("imagemUrl", valorOuPadrao(raca.getImagemUrl(), "Nao informado"));
        resposta.put("descricao", valorOuPadrao(raca.getDescricao(), "Nao informado"));
        resposta.put("fonte", "racas_externas");
        resposta.put("raceId", raca.getRacaIdExterno());
        return resposta;
    }

    private void salvarOuAtualizarRacaExterna(String nomeConsultado, Map<String, Object> corpo) {
        Integer racaIdExterno = converterInteiro(corpo.get("raceId"));

        RacaExternaEntity entidade = null;
        if (racaIdExterno != null) {
            entidade = racaExternaRepository.findByRacaIdExterno(racaIdExterno).orElse(null);
        }
        if (entidade == null) {
            entidade = racaExternaRepository
                    .findFirstByNomeIgnoreCaseOrNomeOriginalIgnoreCase(nomeConsultado, nomeConsultado)
                    .orElseGet(RacaExternaEntity::new);
        }

        entidade.setNome(valorOuPadrao(valorOuNulo(corpo.get("nome")), nomeConsultado));
        entidade.setNomeOriginal(valorOuPadrao(valorOuNulo(corpo.get("nomeExterno")), entidade.getNome()));
        entidade.setRacaIdExterno(racaIdExterno);
        entidade.setTemperamento(valorOuNulo(corpo.get("temperamento")));
        entidade.setVidaMedia(valorOuNulo(corpo.get("expectativaVida")));
        entidade.setAltura(valorOuNulo(corpo.get("altura")));
        entidade.setPeso(valorOuNulo(corpo.get("peso")));
        entidade.setOrigem(valorOuNulo(corpo.get("origemRaca")));
        entidade.setProposito(valorOuNulo(corpo.get("proposito")));
        entidade.setGrupo(valorOuNulo(corpo.get("grupo")));
        entidade.setImagemUrl(valorOuNulo(corpo.get("imagemUrl")));
        entidade.setDescricao(valorOuNulo(corpo.get("descricao")));
        entidade.setDataSincronizacao(LocalDateTime.now());
        entidade.setAtivo(true);

        racaExternaRepository.save(entidade);
    }

    private Integer converterInteiro(Object valor) {
        if (valor == null) {
            return null;
        }
        if (valor instanceof Number numero) {
            return numero.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(valor).trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String valorOuNulo(Object valor) {
        if (valor == null) {
            return null;
        }
        String texto = String.valueOf(valor).trim();
        if (texto.isEmpty() || "Nao informado".equalsIgnoreCase(texto) || "n/a".equalsIgnoreCase(texto)) {
            return null;
        }
        return texto;
    }

    private String valorOuPadrao(String valor, String padrao) {
        String texto = valor == null ? "" : valor.trim();
        return texto.isEmpty() ? padrao : texto;
    }
}
