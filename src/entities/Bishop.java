package entities;

import entities.enums.Color;

public class Bishop extends Piece {

    public Bishop(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol, '♝');
    }

    public void calculatePermittedMoves(Piece[][] positions){
        permittedMoves = new boolean[8][8];
    }
}
