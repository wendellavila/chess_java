package entities;

import entities.enums.Color;

public class Rook extends Piece {

    public Rook(Color color, int initialRow, int initialCol, Board board){
        super(color, initialRow, initialCol, board, '♜');
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        //vertical up
        for(int i = currentRow + 1; i < 8; i++){
            Piece piece = board.getPieceByPosition(i, currentCol);
            if(piece == null){
                permittedMoves[i][currentCol] = true;
            }
            else {
                if(piece.getColor() != getColor()){
                    permittedMoves[i][currentCol] = true;
                    if(piece instanceof King){
                        isCheckingKing = true;
                    }
                }
                break;
            }
        }

        //vertical down
        for(int i = currentRow - 1; i >= 0 ; i--){
            Piece piece = board.getPieceByPosition(i, currentCol);
            if(piece == null){
                permittedMoves[i][currentCol] = true;
            }
            else {
                if(piece.getColor() != getColor()){
                    permittedMoves[i][currentCol] = true;
                    if(piece instanceof King){
                        isCheckingKing = true;
                    }
                }
                break;
            }
        }

        //horizontal right
        for(int j = currentCol + 1; j < 8; j++){
            Piece piece = board.getPieceByPosition(currentRow, j);
            if(piece == null){
                permittedMoves[currentRow][j] = true;
            }
            else {
                if(piece.getColor() == getColor()){
                    permittedMoves[currentRow][j] = true;
                    if(piece instanceof King){
                        isCheckingKing = true;
                    }
                }
                break;
            }
        }

        //horizontal left
        for(int j = currentCol - 1; j >= 0; j--){
            Piece piece = board.getPieceByPosition(currentRow, j);
            if(piece == null){
                permittedMoves[currentRow][j] = true;
            }
            else {
                if(piece.getColor() == getColor()){
                    permittedMoves[currentRow][j] = true;
                    if(piece instanceof King){
                        isCheckingKing = true;
                    }
                }
                break;
            }
        }
    }
}
