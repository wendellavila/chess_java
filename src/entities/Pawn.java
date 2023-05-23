package entities;

import entities.enums.Color;

public class Pawn extends Piece {

    public Pawn(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol, '♟');
    }

    public void calculatePermittedMoves(Piece[][] positions){
        permittedMoves = new boolean[8][8];

        //one square move
        if(getColor() == Color.WHITE){
            if(((currentRow + 1) < 8) && (positions[currentRow][currentCol + 1] == null)){
                permittedMoves[currentRow][currentCol + 1] = true;
            }
        }
        else {
            if(((currentRow - 1) >= 0) && (positions[currentRow][currentCol - 1] == null)){
                permittedMoves[currentRow][currentCol - 1] = true;
            }
        }

        //two square move
        if(moveCount == 0){
            if(getColor() == Color.WHITE){
                if(positions[currentRow][currentCol + 2] == null){
                    permittedMoves[currentRow][currentCol + 2] = true;
                }
            }
            else {
                if(positions[currentRow][currentCol - 2] == null){
                    permittedMoves[currentRow][currentCol - 2] = true;
                }
            }
        }

        //regular capture
        if(getColor() == Color.WHITE){
            if((currentRow + 1) < 8){
                if(((currentCol + 1) < 8) && (positions[currentRow + 1][currentCol + 1] != null) && (positions[currentRow + 1][currentCol + 1].getColor() != getColor())){
                    permittedMoves[currentRow + 1][currentCol + 1] = true;
                }
                if(((currentCol - 1) >= 0) && (positions[currentRow + 1][currentCol - 1] != null) && (positions[currentRow + 1][currentCol - 1].getColor() != getColor())){
                    permittedMoves[currentRow + 1][currentCol - 1] = true;
                }
            }
        }
        else {
            if((currentRow - 1) >= 0){
                if(((currentCol + 1) < 8) && (positions[currentRow - 1][currentCol + 1] != null) && (positions[currentRow - 1][currentCol + 1].getColor() != getColor())) {
                    permittedMoves[currentRow - 1][currentCol + 1] = true;
                }
                if(((currentCol - 1) >= 0) && (positions[currentRow - 1][currentCol - 1] != null) && (positions[currentRow - 1][currentCol - 1].getColor() != getColor())){
                    permittedMoves[currentRow - 1][currentCol - 1] = true;
                }
            }
        }

        //en passant
        if(currentRow == 4 || currentRow == 5){
            if(currentCol + 1 < 8 && positions[currentRow][currentCol + 1] != null &&
                    positions[currentRow][currentCol + 1] instanceof Pawn &&
                    positions[currentRow][currentCol + 1].getColor() != getColor() &&
                    positions[currentRow][currentCol + 1].getMoveCount() == 1){

                if(getColor() == Color.WHITE){
                    permittedMoves[currentRow + 1][currentCol + 1] = true;
                }
                else {
                    permittedMoves[currentRow - 1][currentCol + 1] = true;
                }
            }
            if(currentCol - 1 >= 0 && positions[currentRow][currentCol - 1] != null &&
                    positions[currentRow][currentCol - 1] instanceof Pawn &&
                    positions[currentRow][currentCol - 1].getColor() != getColor() &&
                    positions[currentRow][currentCol - 1].getMoveCount() == 1){
                if(getColor() == Color.WHITE){
                    permittedMoves[currentRow + 1][currentCol - 1] = true;
                }
                else {
                    permittedMoves[currentRow - 1][currentCol - 1] = true;
                }
            }
        }
    }
}
