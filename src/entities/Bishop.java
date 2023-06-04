package entities;

import entities.enums.PieceColor;

public class Bishop extends Piece {

    public Bishop(PieceColor pieceColor, int initialRow, int initialCol, Board board){
        super(pieceColor, initialRow, initialCol, board, '♝', "B");
    }

    public void calculateValidMoves(){
        //resetting status variables
        isCheckingKing = false;
        validMoves = new boolean[8][8];

        //diagonals
        int[][] directions = {{1,1}, {1,-1}, {-1,1}, {-1, -1}};
        for(int[] direction : directions){
            for(int i = 1; i < 8; i++){
                if((position.getRow() + (i * direction[0]) < 8) && (position.getRow() + (i * direction[0]) >= 0) && (position.getCol() + (i * direction[1]) < 8) && (position.getCol() + (i * direction[1]) >= 0)){
                    Piece piece = board.getPiece(position.getRow() + (i * direction[0]), position.getCol() + (i * direction[1]));
                    if(piece == null){
                        validMoves[position.getRow() + (i * direction[0])][position.getCol() + (i * direction[1])] = true;
                    }
                    else {
                        if(piece.getColor() != color){
                            validMoves[position.getRow() + (i * direction[0])][position.getCol() + (i * direction[1])] = true;
                            if(piece instanceof King){
                                isCheckingKing = true;
                            }
                        }
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }
}
