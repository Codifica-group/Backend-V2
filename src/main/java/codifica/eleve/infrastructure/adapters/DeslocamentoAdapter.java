package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.application.ports.out.DeslocamentoPort;
import codifica.eleve.core.domain.agenda.deslocamento.DistanciaETempo;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.exceptions.InternalServerErrorException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@Component
public class DeslocamentoAdapter implements DeslocamentoPort {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${URL_ORS_GEOCODE}")
    private String orsGeocodeUrl;

    @Value("${URL_ORS_DIRECTIONS}")
    private String orsDirectionsUrl;

    @Value("${ORS_API_KEY}")
    private String orsApiKey;

    @Value("${petshop.latitude}")
    private double petshopLatitude;

    @Value("${petshop.longitude}")
    private double petshopLongitude;

    @Override
    public DistanciaETempo calcularDistanciaETempo(Endereco enderecoDestino) {
        double[] coordsCliente = buscarCoordenadas(enderecoDestino);
        return calcularRota(coordsCliente[1], coordsCliente[0]);
    }

    private double[] buscarCoordenadas(Endereco endereco) {
        String enderecoFormatado = String.format("%s %s, %s, SP, %s, Brazil",
                endereco.getRua(), endereco.getNumero(), endereco.getCidade(), endereco.getCep());

        String url = UriComponentsBuilder.fromUriString(orsGeocodeUrl)
                .queryParam("api_key", orsApiKey)
                .queryParam("text", enderecoFormatado)
                .build().toUriString();

        try {
            JsonNode body = restTemplate.getForObject(url, JsonNode.class);
            if (body == null || !body.has("features") || body.get("features").isEmpty()) {
                throw new NotFoundException("Não foi possível obter coordenadas para o endereço informado.");
            }
            JsonNode coords = body.get("features").get(0).get("geometry").get("coordinates");
            return new double[]{coords.get(0).asDouble(), coords.get(1).asDouble()};
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar coordenadas na API externa: " + e.getMessage());
        }
    }

    private DistanciaETempo calcularRota(double latCliente, double lonCliente) {
        String url = String.format(Locale.US,
                "%s?api_key=%s&start=%f,%f&end=%f,%f",
                orsDirectionsUrl, orsApiKey, lonCliente, latCliente, petshopLongitude, petshopLatitude);

        try {
            JsonNode body = restTemplate.getForObject(url, JsonNode.class);
            if (body == null || !body.has("features") || body.get("features").isEmpty()) {
                throw new NotFoundException("Não foi possível calcular a rota para as coordenadas informadas.");
            }
            JsonNode properties = body.get("features").get(0).get("properties").get("segments").get(0);
            double distanciaMetros = properties.get("distance").asDouble();
            double tempoSegundos = properties.get("duration").asDouble();

            return new DistanciaETempo(distanciaMetros / 1000, tempoSegundos / 60);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao consultar rota na API externa: " + e.getMessage());
        }
    }
}
