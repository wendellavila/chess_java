package entities;

import entities.enums.Color;

public class Pawn extends Piece {

    public Pawn(Color color, int initialRow, int initialCol, Board board){
        super(color, initialRow, initialCol, board, '♟');
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        //one square row movement
        //direction is different for white and black
        int i = getColor() == Color.WHITE ? 1 : -1;
        if((currentRow + i < 8) && (currentRow + i >= 0) && (board.getPieceByPosition(currentRow, currentCol + i) == null)){
            permittedMoves[currentRow][currentCol + i] = true;
        }

        //two square move
        if(moveCount == 0){
            //direction is different for white and black
            i = getColor() == Color.WHITE ? 2 : -2;
            if(board.getPieceByPosition(currentRow, currentCol + i) == null){
                permittedMoves[currentRow][currentCol + i] = true;
            }
        }

        //regular capture
        if((currentRow + i < 8) && (currentRow + i >= 0)){
            for(int j : new int[]{1, -1}){
                if((currentCol + j < 8) && (currentCol + j >= 0)){
                    Piece piece = board.getPieceByPosition(currentRow + i, currentCol + j);
                    if(piece != null && piece.getColor() != getColor()){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }

        //en passant
        if((getColor() == Color.WHITE && currentRow == 5) || (getColor() == Color.BLACK && currentRow == 4)){
            for(int j : new int[]{1, -1}){
                if((currentCol + j < 8) && (currentCol + j >= 0)){
                    Piece piece = board.getPieceByPosition(currentRow, currentCol + j);
                    if(piece instanceof Pawn && piece.getColor() != getColor() && piece.getMoveCount() == 1){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                    }
                }
            }
        }
    }
}
