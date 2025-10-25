package codifica.eleve.core.domain.shared;

import java.util.List;

public record Pagina<T> (List<T> dados, int totalPaginas) {

    public Pagina(List<T> dados, int totalPaginas) {
        this.dados = dados;
        this.totalPaginas = totalPaginas;
    }
}
