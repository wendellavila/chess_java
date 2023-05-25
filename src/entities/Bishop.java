package entities;

import entities.enums.Color;

public class Bishop extends Piece {

    public Bishop(Color color, int initialRow, int initialCol, Board board){
        super(color, initialRow, initialCol, board, '♝');
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];
    }
}
