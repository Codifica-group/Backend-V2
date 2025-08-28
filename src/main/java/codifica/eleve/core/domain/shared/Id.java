package codifica.eleve.core.domain.shared;

public class Id {
    private Integer Value;

    public Id(Integer value) {
        Value = value;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer value) {
        Value = value;
    }
}
