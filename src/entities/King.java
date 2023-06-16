package entities;

import entities.enums.PieceColor;

public class King extends Piece {

    public King(PieceColor color, Position position, Board board){
        super(color, position, board, '♚', "K");
    }

    public boolean isMoveCastling(int row, int col){
        if(moveCount == 0 && row == position.getRow()){

            Piece rookPosition = null;
            if(col == 1){
                rookPosition = board.getPiece(position.getRow(), 0);
            }
            else if(col == 6){
                rookPosition = board.getPiece(position.getRow(), 7);
            }

            if(rookPosition instanceof Rook &&
                    rookPosition.getColor() == color &&
                    rookPosition.getMoveCount() == 0){
                int min = col < position.getCol() ? col : position.getCol() + 1;
                int max = col < position.getCol() ? position.getCol() : col + 1;
                for(int j = min; j < max; j++){
                    if(board.getPiece(position.getRow(), j) != null){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void calculateValidMoves(){
        //resetting status variables
        isCheckingKing = false;
        validMoves = new boolean[8][8];

        //regular move
        for(int i : new int[]{-1, 0, 1}){
            for(int j : new int[]{-1, 0, 1}) {
                if(i+j != 0 && new Position(position.getRow() + i, position.getCol() + j).isValid()){
                    Piece piece = board.getPiece(position.getRow() + i, position.getCol() + j);
                    if(piece == null){
                        validMoves[position.getRow() + i][position.getCol() + j] = true;
                    }
                    else {
                        if(piece.getColor() != color){
                            validMoves[position.getRow() + i][position.getCol() + j] = true;
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

                Piece rookPosition = destinationCol == 1 ? board.getPiece(position.getRow(), 0) :
                        board.getPiece(position.getRow(), 7);

                boolean isPieceBetween = false;

                if(rookPosition instanceof Rook &&
                        rookPosition.getColor() == color &&
                        rookPosition.getMoveCount() == 0){
                    int min = destinationCol < position.getCol() ? destinationCol : position.getCol() + 1;
                    int max = destinationCol < position.getCol() ? position.getCol() : destinationCol + 1;
                    for(int j = min; j < max; j++){
                        if(board.getPiece(position.getRow(), j) != null){
                            isPieceBetween = true;
                            break;
                        }
                    }
                    validMoves[position.getRow()][destinationCol] = !isPieceBetween;
                }
            }
        }
    }
}
