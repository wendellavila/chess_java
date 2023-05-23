package entities;

import entities.enums.Color;

public class Knight extends Piece {

    public Knight(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol);
        super.setIcon('♞');
    }
}
