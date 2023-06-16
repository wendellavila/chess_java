package entities;

import entities.enums.PieceColor;
import entities.exceptions.GameOverException;

import java.util.List;

public class King extends Piece {

    public King(PieceColor color, Position position, Board board){
        super(color, position, board, '♚', "K");
    }

    public boolean isMoveCastling(int row, int col){
        if(moveCount == 0 && row == position.getRow()){

            Piece rookPosition = null;
            if(col == 1){
                rookPosition = board.getPiece(new Position(position.getRow(), 0));
            }
            else if(col == 6){
                rookPosition = board.getPiece(new Position(position.getRow(), 7));
            }

            if(rookPosition instanceof Rook &&
                    rookPosition.getColor() == color &&
                    rookPosition.getMoveCount() == 0){
                int min = col < position.getCol() ? col : position.getCol() + 1;
                int max = col < position.getCol() ? position.getCol() : col + 1;
                for(int j = min; j < max; j++){
                    if(board.getPiece(new Position(position.getRow(), j)) != null){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean moveCausesCheck(Position destination){
        List<Piece> oppositePieces = board.getPieceListByColor(
                color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE
        );

        for(Piece piece : oppositePieces){
            if(piece.isMoveValid(destination)){
                return true;
            }
        }
        return false;
    }

    public void calculateValidMoves() throws GameOverException {
        //resetting status variables
        resetMovesInfo();
        Position destination;

        //regular move
        for(int i : new int[]{-1, 0, 1}){
            for(int j : new int[]{-1, 0, 1}) {
                destination = new Position(position.getRow() + i, position.getCol() + j);
                if(i+j != 0 && destination.isValid()){
                    Piece piece = board.getPiece(destination);
                    if(piece == null){
                        setValidMove(destination);
                    }
                    else {
                        if(piece.getColor() != color){
                            setValidMove(destination);
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
                destination = new Position(position.getRow(), destinationCol);
                Piece rookPosition = destinationCol == 1 ? board.getPiece(new Position(position.getRow(), 0)) :
                        board.getPiece(new Position(position.getRow(), 7));

                boolean isPieceBetween = false;

                if(rookPosition instanceof Rook &&
                        rookPosition.getColor() == color &&
                        rookPosition.getMoveCount() == 0){
                    int min = destinationCol < position.getCol() ? destinationCol : position.getCol() + 1;
                    int max = destinationCol < position.getCol() ? position.getCol() : destinationCol + 1;
                    for(int j = min; j < max; j++){
                        if(board.getPiece(new Position(position.getRow(), j)) != null){
                            isPieceBetween = true;
                            break;
                        }
                    }
                    if(!isPieceBetween){
                        setValidMove(destination);
                    }
                }
            }
        }

        if(!hasValidMoves && board.getPieceCountByColor(getColor()) == 1){
            board.addToLatestPlays(
                    new NotationEntry("½-½", PieceColor.NONE, "½")
            );
            throw new GameOverException("Draw by stalemate!");
        }
    }
}
