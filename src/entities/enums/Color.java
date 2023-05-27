package entities.enums;

public enum Color {
    WHITE,
    BLACK,
    ;

    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "Blacks";
            case WHITE -> "Whites";
            default -> "";
        };
    }
}
