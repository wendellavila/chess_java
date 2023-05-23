package entities;

import entities.enums.Color;

public class Pawn extends Piece {

    private int initialRow, initialCol;

    public Pawn(Color color, int initialRow, int initialCol){
        super(color, initialRow, initialCol, '♟');
        this.initialRow = initialRow;
        this.initialCol = initialCol;
    }

    public void calculatePermittedMoves(Piece[][] positions){
        permittedMoves = new boolean[8][8];

        //one square move
        if(super.getColor() == Color.WHITE){
            if((currentRow + 1) < 8){
                if(positions[currentRow][currentCol + 1] == null)){
                    permittedMoves[currentRow][currentCol + 1] = true;
                }
            }
        }
        else {
            if((currentRow - 1) >= 0){
                if(positions[currentRow][currentCol - 1] == null){
                    permittedMoves[currentRow][currentCol - 1] = true;
                }
            }
        }

        //two square move
        if(currentRow == initialRow){
            if(super.getColor() == Color.WHITE){
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
        if(super.getColor() == Color.WHITE){
            if((currentRow + 1) < 8){
                if((currentCol + 1) < 8){
                    if(positions[currentRow + 1][currentCol + 1] != null && positions[currentRow + 1][currentCol + 1].getColor() != super.getColor){
                        permittedMoves[currentRow + 1][currentCol + 1] = true;
                    }
                }
                if((currentCol - 1) >= 0){
                    if(positions[currentRow + 1][currentCol - 1] != null && positions[currentRow + 1][currentCol - 1].getColor() != super.getColor){
                        permittedMoves[currentRow + 1][currentCol - 1] = true;
                    }
                }
            }
        }
        else {
            if((currentRow - 1) >= 0){
                if((currentCol + 1) < 8){
                    if(positions[currentRow - 1][currentCol + 1] != null && positions[currentRow - 1][currentCol + 1].getColor() != super.getColor){
                        permittedMoves[currentRow - 1][currentCol + 1] = true;
                    }
                }
                if((currentCol - 1) >= 0){
                    if(positions[currentRow - 1][currentCol - 1] != null && positions[currentRow - 1][currentCol - 1].getColor() != super.getColor){
                        permittedMoves[currentRow - 1][currentCol - 1] = true;
                    }
                }
            }
        }

        //en passant

    }
}
