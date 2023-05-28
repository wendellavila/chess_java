package entities;

import entities.enums.PieceColor;

public class Pawn extends Piece {

    public Pawn(PieceColor pieceColor, int initialRow, int initialCol, Board board){
        super(pieceColor, initialRow, initialCol, board, '♟', "");
    }

    public boolean isMoveEnPassant(int destinationRow, int destinationCol){
        int direction = pieceColor == PieceColor.WHITE ? -1 : 1 ;
        Piece enPassantCapture = board.getPieceByPosition(destinationRow + direction, destinationCol);

        if(permittedMoves[destinationRow][destinationCol] == true &&
                board.getPieceByPosition(destinationRow, destinationCol) == null &&
                enPassantCapture instanceof Pawn &&
                enPassantCapture.getColor() != pieceColor &&
                enPassantCapture.getMoveCount() == 1 &&
                enPassantCapture.getLastMove() == board.getMoveCount()){
            return true;
        }
        return false;
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        //two square move
        if(moveCount == 0){
            //direction is different for white and black
            int i = pieceColor == PieceColor.WHITE ? 2 : -2;
            if(board.getPieceByPosition(currentRow + i, currentCol) == null){
                permittedMoves[currentRow + i][currentCol] = true;
            }
        }

        //direction is different for white and black
        int i = pieceColor == PieceColor.WHITE ? 1 : -1;
        //one square movement
        if((currentRow + i < 8) && (currentRow + i >= 0) && (board.getPieceByPosition(currentRow + i, currentCol) == null)){
            permittedMoves[currentRow + i][currentCol] = true;
        }
        //regular capture
        if((currentRow + i < 8) && (currentRow + i >= 0)){
            for(int j : new int[]{1, -1}){
                if((currentCol + j < 8) && (currentCol + j >= 0)){
                    Piece piece = board.getPieceByPosition(currentRow + i, currentCol + j);
                    if(piece != null && piece.getColor() != pieceColor){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }
        //en passant
        if((getColor() == PieceColor.WHITE && currentRow == 4) || (getColor() == PieceColor.BLACK && currentRow == 3)){
            for(int j : new int[]{1, -1}){
                if((currentCol + j < 8) && (currentCol + j >= 0)){
                    Piece piece = board.getPieceByPosition(currentRow, currentCol + j);
                    if(piece instanceof Pawn && piece.getColor() != pieceColor && piece.getMoveCount() == 1 &&
                            piece.getLastMove() == board.getMoveCount() &&
                            board.getPieceByPosition(currentRow + i, currentCol + j) == null){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                    }
                }
            }
        }
    }
}
