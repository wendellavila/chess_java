package entities;

import entities.enums.PieceColor;

public class Pawn extends Piece {

    public Pawn(PieceColor color, Position position, Board board){
        super(color, position, board, '♟', "");
    }

    public boolean isMovePromotion(int row, int col){
        if(validMoves[row][col]){
            if(color == PieceColor.WHITE && position.getRow() == 6 && row == 7){
                return true;
            }
            else if(color == PieceColor.BLACK && position.getRow() == 1 && row == 0){
                return true;
            }
        }
        return false;
    }

    public boolean isMoveEnPassant(int row, int col){
        int direction = color == PieceColor.WHITE ? -1 : 1 ;
        Piece enPassantCapture = board.getPiece(row + direction, col);

        if(validMoves[row][col] == true &&
                board.getPiece(row, col) == null &&
                enPassantCapture instanceof Pawn &&
                enPassantCapture.getColor() != color &&
                enPassantCapture.getMoveCount() == 1 &&
                enPassantCapture.getLastMove() == board.getMoveCount()){
            return true;
        }
        return false;
    }

    public void calculateValidMoves(){
        //resetting status variables
        isCheckingKing = false;
        validMoves = new boolean[8][8];

        //two square move
        if(moveCount == 0){
            //direction is different for white and black
            int i = color == PieceColor.WHITE ? 2 : -2;
            if(board.getPiece(position.getRow() + i, position.getCol()) == null){
                validMoves[position.getRow() + i][position.getCol()] = true;
            }
        }

        //direction is different for white and black
        int i = color == PieceColor.WHITE ? 1 : -1;
        //one square movement
        if(new Position(position.getRow() + i, position.getCol()).isValid() && board.getPiece(position.getRow() + i, position.getCol()) == null){
            validMoves[position.getRow() + i][position.getCol()] = true;
        }
        //regular capture
        if(new Position(position.getRow() + i, position.getCol()).isValid()){
            for(int j : new int[]{1, -1}){
                if((position.getCol() + j < 8) && (position.getCol() + j >= 0)){
                    Piece piece = board.getPiece(position.getRow() + i, position.getCol() + j);
                    if(piece != null && piece.getColor() != color){
                        validMoves[position.getRow() + i][position.getCol() + j] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }
        //en passant
        if((getColor() == PieceColor.WHITE && position.getRow() == 4) || (getColor() == PieceColor.BLACK && position.getRow() == 3)){
            for(int j : new int[]{1, -1}){
                if(new Position(position.getRow(), position.getCol() + j).isValid()){
                    Piece piece = board.getPiece(position.getRow(), position.getCol() + j);
                    if(piece instanceof Pawn && piece.getColor() != color && piece.getMoveCount() == 1 &&
                            piece.getLastMove() == board.getMoveCount() &&
                            board.getPiece(position.getRow() + i, position.getCol() + j) == null){
                        validMoves[position.getRow() + i][position.getCol() + j] = true;
                    }
                }
            }
        }
    }
}
