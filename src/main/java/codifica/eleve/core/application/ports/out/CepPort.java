package codifica.eleve.core.application.ports.out;

import codifica.eleve.core.domain.shared.Endereco;

public interface CepPort {
    Endereco findByCep(String cep);
}
