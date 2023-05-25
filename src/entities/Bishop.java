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

        //diagonal up right
        for(int i = 0; i < 8; i++){
            if((currentRow + i < 8) && (currentRow + i >= 0) && (currentCol + i < 8) && (currentCol + i >= 0)){
                Piece piece = board.getPieceByPosition(currentRow + i, currentCol + i);
                if(piece == null){
                    permittedMoves[currentRow + i][currentCol + i] = true;
                }
                else {
                    if(piece.getColor() != getColor()){
                        permittedMoves[currentRow + i][currentCol + i] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                    break;
                }
            }
        }

        //diagonal up left
        for(int i = 0; i < 8; i++){
            if((currentRow + i < 8) && (currentRow + i >= 0) && (currentCol - i < 8) && (currentCol - i >= 0)){
                Piece piece = board.getPieceByPosition(currentRow + i, currentCol - i);
                if(piece == null){
                    permittedMoves[currentRow + i][currentCol - i] = true;
                }
                else {
                    if(piece.getColor() != getColor()){
                        permittedMoves[currentRow + i][currentCol - i] = true;
                        if (piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                    break;
                }
            }
        }

        //diagonal down right
        for(int i = 0; i < 8; i++){
            if((currentRow - i < 8) && (currentRow - i >= 0) && (currentCol + i < 8) && (currentCol + i >= 0)){
                Piece piece = board.getPieceByPosition(currentRow - i, currentCol + i);
                if(piece == null){
                    permittedMoves[currentRow - i][currentCol + i] = true;
                }
                else {
                    if(piece.getColor() != getColor()){
                        permittedMoves[currentRow - i][currentCol + i] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                    break;
                }
            }
        }

        //diagonal down left
        for(int i = 0; i < 8; i++){
            if((currentRow - i < 8) && (currentRow - i >= 0) && (currentCol - i < 8) && (currentCol - i >= 0)){
                Piece piece = board.getPieceByPosition(currentRow - i, currentCol - i);
                if(piece == null){
                    permittedMoves[currentRow - i][currentCol - i] = true;
                }
                else {
                    if(piece.getColor() != getColor()){
                        permittedMoves[currentRow - i][currentCol - i] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                    break;
                }
            }
        }
    }
}
