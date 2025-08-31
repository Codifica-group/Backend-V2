package codifica.eleve.infrastructure.adapters;

import codifica.eleve.core.application.ports.out.CepPort;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.exceptions.InternalServerErrorException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.CepDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CepAdapter implements CepPort {

    @Value("${URL_VIACEP}")
    private String viaCepUrl;

    @Override
    public Endereco findByCep(String cep) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            CepDTO cepDTO = restTemplate.getForObject(viaCepUrl, CepDTO.class, cep);

            if (cepDTO == null || cepDTO.isErro()) {
                throw new NotFoundException("CEP não encontrado: " + cep);
            }

            return new Endereco(
                    cepDTO.getCep().replace("-", ""),
                    cepDTO.getLogradouro(),
                    null,
                    cepDTO.getBairro(),
                    cepDTO.getLocalidade(),
                    cepDTO.getComplemento()
            );

        } catch (Exception e) {
            throw new InternalServerErrorException("Erro interno na API VIACEP: " + e.getMessage());
        }
    }
}
