package entities;

import entities.enums.Color;

public class King extends Piece {

    public King(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol, '♚');
    }

    public void calculatePermittedMoves(Piece[][] positions){
        permittedMoves = new boolean[8][8];
    }
}
