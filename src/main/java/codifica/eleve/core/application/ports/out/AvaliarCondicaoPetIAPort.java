package codifica.eleve.core.application.ports.out;

import codifica.eleve.core.domain.raca.Raca;

public interface AvaliarCondicaoPetIAPort {
    String avaliarCondicao(byte[] imageBytes, String mimeType, String servicosSolicitados, Raca raca);
}
