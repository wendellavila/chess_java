package entities;

import entities.enums.PieceColor;
import entities.utils.ANSICodes;

public class NotationEntry {
    private final String notation;
    private final PieceColor pieceColor;
    private final String icon;

    public NotationEntry(String notation, PieceColor pieceColor, String icon) {
        this.notation = notation;
        this.pieceColor = pieceColor;
        this.icon = icon;
    }

    @Override
    public String toString(){
        final String bgColor = pieceColor == PieceColor.WHITE ? ANSICodes.ANSI_GREEN_BG : ANSICodes.ANSI_BROWN_BG;
        final String textColor = pieceColor == PieceColor.WHITE ? ANSICodes.ANSI_WHITE : ANSICodes.ANSI_BLACK;

        return bgColor + textColor + " " + icon + " " + ANSICodes.ANSI_RESET + " " + notation;
    }
}
