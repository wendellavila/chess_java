package entities;

import entities.enums.Color;

public class Pawn extends Piece {

    public Pawn(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol);
        super.setIcon('♟');
    }
}
