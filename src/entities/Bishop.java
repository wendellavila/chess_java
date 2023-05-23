package entities;

import entities.enums.Color;

public class Bishop extends Piece {

    public Bishop(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol);
        super.setIcon('♝');
    }
}
