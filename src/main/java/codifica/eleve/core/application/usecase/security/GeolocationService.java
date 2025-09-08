package codifica.eleve.core.application.usecase.security;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

    private static final Logger logger = LoggerFactory.getLogger(GeolocationService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public String getGeolocation(String ip) {
        if (ip == null || ip.isEmpty() || isPrivateIp(ip)) {
            return "Local IP";
        }
        try {
            String url = "http://ip-api.com/json/" + ip;
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            if (response != null && "success".equals(response.get("status").asText())) {
                String country = response.get("country").asText();
                String city = response.get("city").asText();
                String isp = response.get("isp").asText();
                return String.format("Country: %s, City: %s, ISP: %s", country, city, isp);
            }
        } catch (Exception e) {
            logger.error("Falha ao obter geolocalização para o IP: {}", ip, e);
        }
        return "N/A";
    }

    private boolean isPrivateIp(String ip) {
        return ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.") || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip);
    }
}
