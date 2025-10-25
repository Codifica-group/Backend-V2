package codifica.eleve.core.domain.shared;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Pagina<T>(List<T> dados, int totalPaginas) {

    public Pagina(List<T> dados, int totalPaginas) {
        this.dados = dados;
        this.totalPaginas = totalPaginas;
    }

    public <U> Pagina<U> map(Function<? super T, ? extends U> mapper) {
        List<U> mappedData = this.dados.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new Pagina<>(mappedData, this.totalPaginas);
    }
}
