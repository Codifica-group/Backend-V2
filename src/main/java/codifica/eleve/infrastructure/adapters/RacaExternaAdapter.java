package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.domain.shared.exceptions.InternalServerErrorException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
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
import java.util.Map;

@Component
public class RacaExternaAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RacaExternaAdapter.class);

    @Value("${URL_DADOS_PY}")
    private String urlDadosPy;

    public Map<String, Object> obterInfoRaca(String nomeRaca) {
        String nomeLimpo = nomeRaca == null ? "" : nomeRaca.trim();
        if (nomeLimpo.length() < 2) {
            throw new codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException(
                    "Nome da raca deve ter pelo menos 2 caracteres."
            );
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
}
