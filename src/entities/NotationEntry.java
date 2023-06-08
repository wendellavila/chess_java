package entities;

import entities.enums.PieceColor;
import entities.utils.ANSICodes;

public class NotationEntry {
    private final String notation;
    private final PieceColor color;
    private final String icon;

    public NotationEntry(String notation, PieceColor color, String icon) {
        this.notation = notation;
        this.color = color;
        this.icon = icon;
    }

    @Override
    public String toString(){

        final String textColor = color == PieceColor.WHITE ? ANSICodes.ANSI_WHITE : ANSICodes.ANSI_BLACK;
        final String bgColor = switch(color){
            case WHITE -> ANSICodes.ANSI_GREEN_BG;
            case BLACK -> ANSICodes.ANSI_BROWN_BG;
            case NONE -> ANSICodes.ANSI_BLUE_BG;
        };
        return bgColor + textColor + " " + icon + " " + ANSICodes.ANSI_RESET + " " + notation;
    }
}
