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

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for(int[] direction : directions){
            for(int i = 1; i < 8; i++){
                if((currentRow + (i * direction[0]) < 8) && (currentRow + (i * direction[0]) >= 0) && (currentCol + (i * direction[1]) < 8) && (currentCol + (i * direction[1]) >= 0)){
                    Piece piece = board.getPieceByPosition(currentRow + (i * direction[0]), currentCol + (i * direction[1]));
                    if(piece == null){
                        System.out.println('a');
                        permittedMoves[currentRow + (i * direction[0])][currentCol + (i * direction[1])] = true;
                    }
                    else {
                        if(piece.getColor() != getColor()){
                            System.out.println('b');
                            permittedMoves[currentRow + (i * direction[0])][currentCol + (i * direction[1])] = true;
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
