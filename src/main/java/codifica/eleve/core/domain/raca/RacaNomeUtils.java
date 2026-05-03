package codifica.eleve.core.domain.raca;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;

public final class RacaNomeUtils {

    private static final Map<String, String> RACA_DE_PARA = Map.ofEntries(
            Map.entry("lulu da pomerania", "pomeranian"),
            Map.entry("spitz alemao anao", "pomeranian"),
            Map.entry("spitz alemao", "pomeranian"),
            Map.entry("salsicha", "dachshund"),
            Map.entry("buldogue frances", "french bulldog"),
            Map.entry("bulldog frances", "french bulldog"),
            Map.entry("buldogue ingles", "english bulldog"),
            Map.entry("bulldog ingles", "english bulldog"),
            Map.entry("pastor alemao", "german shepherd dog"),
            Map.entry("maltes", "maltese"),
            Map.entry("shihtzu", "shih tzu"),
            Map.entry("shih tzu", "shih tzu"),
            Map.entry("labrador", "labrador retriever"),
            Map.entry("golden", "golden retriever")
    );

    private RacaNomeUtils() {
    }

    public static String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        String semAcento = Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        return semAcento
                .toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("\\s+", " ");
    }

    public static String canonicalizar(String valor) {
        String nomeNormalizado = normalizar(valor);
        if (nomeNormalizado.isEmpty()) {
            return "";
        }

        return normalizar(RACA_DE_PARA.getOrDefault(nomeNormalizado, nomeNormalizado));
    }
}
