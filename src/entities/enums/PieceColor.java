package entities.enums;

public enum PieceColor {
    WHITE,
    BLACK,
    NONE,
    ;

    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "Black";
            case WHITE -> "White";
            case NONE -> "None";
        };
    }
}
