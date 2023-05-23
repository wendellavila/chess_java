package entities;

import entities.enums.Color;

public class Queen extends Piece {

    public Queen(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol, '♛');
    }

    public void calculatePermittedMoves(Piece[][] positions){
        permittedMoves = new boolean[8][8];

        //horizontal right
        for(int i = currentRow; i < 8; i++){
            if(positions[i][currentCol] == null){
                permittedMoves[i][currentCol] = true;
            }
            else {
                if(positions[i][currentCol].getColor() != getColor()){
                    permittedMoves[i][currentCol] = true;
                }
                break;
            }
        }

        //horizontal left
        for(int i = currentRow; i >= 0 ; i--){
            if(positions[i][currentCol] == null){
                permittedMoves[i][currentCol] = true;
            }
            else {
                if(positions[i][currentCol].getColor() != getColor()){
                    permittedMoves[i][currentCol] = true;
                }
                break;
            }
        }

        //vertical up
        for(int j = currentCol; j < 8; j++){
            if(positions[currentRow][j] == null){
                permittedMoves[currentRow][j] = true;
            }
            else {
                if(positions[currentRow][j].getColor() == getColor()){
                    permittedMoves[currentRow][j] = true;
                }
                break;
            }
        }

        //vertical down
        for(int j = currentCol; j >= 0; j--){
            if(positions[currentRow][j] == null){
                permittedMoves[currentRow][j] = true;
            }
            else {
                if(positions[currentRow][j].getColor() == getColor()){
                    permittedMoves[currentRow][j] = true;
                }
                break;
            }
        }
    }
}
