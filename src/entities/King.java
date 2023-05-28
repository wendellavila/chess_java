package entities;

import entities.enums.PieceColor;

public class King extends Piece {

    public King(PieceColor pieceColor, int initialRow, int initialCol, Board board){
        super(pieceColor, initialRow, initialCol, board, '♚', "K");
    }

    public boolean isMoveCastling(int destinationRow, int destinationCol){
        if(moveCount == 0 && destinationRow == currentRow){

            Piece rookPosition = null;
            if(destinationCol == 1){
                rookPosition = board.getPieceByPosition(currentRow, 0);
            }
            else if(destinationCol == 6){
                rookPosition = board.getPieceByPosition(currentRow, 7);
            }

            if(rookPosition instanceof Rook &&
                    rookPosition.getColor() == pieceColor &&
                    rookPosition.getMoveCount() == 0){
                int min = destinationCol < currentCol ? destinationCol : currentCol + 1;
                int max = destinationCol < currentCol ? currentCol : destinationCol + 1;
                for(int j = min; j < max; j++){
                    if(board.getPieceByPosition(currentRow, j) != null){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        //regular move
        for(int i : new int[]{-1, 0, 1}){
            for(int j : new int[]{-1, 0, 1}) {
                if((i+j != 0) && (currentRow + i < 8) && (currentRow + i >=0) && (currentCol + j < 8) && (currentCol + j >=0)){
                    Piece piece = board.getPieceByPosition(currentRow + i, currentCol + j);
                    if(piece == null){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                    }
                    else {
                        if(piece.getColor() != pieceColor){
                            permittedMoves[currentRow + i][currentCol + j] = true;
                            if(piece instanceof King){
                                isCheckingKing = true;
                            }
                        }
                    }
                }
            }
        }

        //castling
        if(moveCount == 0){
            int[] destinationCols = {1,6};
            for(int destinationCol : destinationCols){

                Piece rookPosition = destinationCol == 1 ? board.getPieceByPosition(currentRow, 0) :
                        board.getPieceByPosition(currentRow, 7);

                boolean hasNonNull = false;

                if(rookPosition instanceof Rook &&
                        rookPosition.getColor() == pieceColor &&
                        rookPosition.getMoveCount() == 0){
                    int min = destinationCol < currentCol ? destinationCol : currentCol + 1;
                    int max = destinationCol < currentCol ? currentCol : destinationCol + 1;
                    for(int j = min; j < max; j++){
                        if(board.getPieceByPosition(currentRow, j) != null){
                            hasNonNull = true;
                            break;
                        }
                    }
                    permittedMoves[currentRow][destinationCol] = !hasNonNull;
                }
            }
        }
    }
}
