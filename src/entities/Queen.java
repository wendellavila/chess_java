package entities;

import entities.enums.Color;

public class Queen extends Piece {

    public Queen(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol);
        super.setIcon('♛');
    }
}
