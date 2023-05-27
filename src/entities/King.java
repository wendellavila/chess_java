package entities;

import entities.enums.PieceColor;

public class King extends Piece {

    public King(PieceColor pieceColor, int initialRow, int initialCol, Board board){
        super(pieceColor, initialRow, initialCol, board, '♚', "K");
    }

    public boolean isMoveCastling(int destinationRow, int destinationCol){
        return false;
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        for(int i : new int[]{-1, 0, 1}){
            for(int j : new int[]{-1, 0, 1}) {
                if((i+j != 0) && (currentRow + i < 8) && (currentRow + i >=0) && (currentCol + j < 8) && (currentCol + j >=0)){
                    Piece piece = board.getPieceByPosition(currentRow + i, currentCol + j);
                    if(piece == null){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                    }
                    else {
                        if(piece.getColor() != getColor()){
                            permittedMoves[currentRow + i][currentCol + j] = true;
                            if(piece instanceof King){
                                isCheckingKing = true;
                            }
                        }
                    }
                }
            }
        }
    }
}
