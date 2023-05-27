package entities;

import entities.enums.PieceColor;

public class Pawn extends Piece {

    public Pawn(PieceColor pieceColor, int initialRow, int initialCol, Board board){
        super(pieceColor, initialRow, initialCol, board, '♟');
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        //one square row movement
        //direction is different for white and black
        int i = getColor() == PieceColor.WHITE ? 1 : -1;
        if((currentRow + i < 8) && (currentRow + i >= 0) && (board.getPieceByPosition(currentRow + i, currentCol) == null)){
            permittedMoves[currentRow + i][currentCol] = true;
        }

        //two square move
        if(moveCount == 0){
            //direction is different for white and black
            i = getColor() == PieceColor.WHITE ? 2 : -2;
            if(board.getPieceByPosition(currentRow + i, currentCol) == null){
                permittedMoves[currentRow + i][currentCol] = true;
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
        if((getColor() == PieceColor.WHITE && currentRow == 5) || (getColor() == PieceColor.BLACK && currentRow == 4)){
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
