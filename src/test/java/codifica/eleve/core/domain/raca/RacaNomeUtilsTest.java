package codifica.eleve.core.domain.raca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RacaNomeUtilsTest {

    @Test
    void deveCanonicalizarLuluDaPomerania() {
        assertEquals("pomeranian", RacaNomeUtils.canonicalizar("Lulu da Pomerânia"));
        assertEquals("pomeranian", RacaNomeUtils.canonicalizar("Lulu da Pomerania"));
        assertEquals("pomeranian", RacaNomeUtils.canonicalizar("Spitz Alemão"));
        assertEquals("pomeranian", RacaNomeUtils.canonicalizar("Pomeranian"));
    }
}
