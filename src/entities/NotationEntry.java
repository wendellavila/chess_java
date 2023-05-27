package entities;

import entities.enums.PieceColor;
import entities.utils.ANSIColors;

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
        final String bgColor = pieceColor == PieceColor.WHITE ? ANSIColors.ANSI_GREEN_BG : ANSIColors.ANSI_BROWN_BG;
        final String textColor = pieceColor == PieceColor.WHITE ? ANSIColors.ANSI_WHITE : ANSIColors.ANSI_BLACK;

        return bgColor + textColor + " " + icon + " " + ANSIColors.ANSI_RESET + " " + notation;
    }
}
