package entities;

import entities.enums.Color;

public class King extends Piece {

    private int initialRow, initialCol;

    public King(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol, '♚');
        this.initialRow = initialRow;
        this.initialCol = initialCol;
    }

    public void calculatePermittedMoves(Piece[][] positions){
        permittedMoves = new boolean[8][8];
    }
}
