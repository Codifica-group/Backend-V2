package codifica.eleve.core.application.usecase.cliente.cep;

import codifica.eleve.core.application.ports.out.CepPort;
import codifica.eleve.core.domain.shared.Endereco;

public class FindCepUseCase {

    private final CepPort cepPort;

    public FindCepUseCase(CepPort cepPort) {
        this.cepPort = cepPort;
    }

    public Endereco execute(String cep) {
        return cepPort.findByCep(cep);
    }
}
