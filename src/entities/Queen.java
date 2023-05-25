package entities;

import entities.enums.Color;

public class Queen extends Piece {

    public Queen(Color color, int initialRow, int initialCol, Board board){
        super(color, initialRow, initialCol, board, '♛');
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        //horizontal right
        for(int i = currentRow; i < 8; i++){
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

        //horizontal left
        for(int i = currentRow; i >= 0 ; i--){
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

        //vertical up
        for(int j = currentCol; j < 8; j++){
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

        //vertical down
        for(int j = currentCol; j >= 0; j--){
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
