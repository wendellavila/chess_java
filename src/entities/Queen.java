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
