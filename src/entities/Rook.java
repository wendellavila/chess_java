package entities;

import entities.enums.Color;

public class Rook extends Piece {

    public Rook(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol);
        super.setIcon('♜');
    }
}
