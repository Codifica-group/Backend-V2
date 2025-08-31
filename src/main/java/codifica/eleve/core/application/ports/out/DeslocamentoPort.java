package codifica.eleve.core.application.ports.out;

import codifica.eleve.core.domain.agenda.deslocamento.DistanciaETempo;
import codifica.eleve.core.domain.shared.Endereco;

public interface DeslocamentoPort {
    DistanciaETempo calcularDistanciaETempo(Endereco enderecoDestino);
}
