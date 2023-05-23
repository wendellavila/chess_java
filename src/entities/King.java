package entities;

import entities.enums.Color;

public class King extends Piece {

    public King(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol);
        super.setIcon('♚');
    }
}
