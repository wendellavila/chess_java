package entities;

import entities.enums.PieceColor;

public abstract class Piece {

    private final char icon;
    private final String notationSymbol;
    protected final PieceColor color;
    protected Position position;
    protected Board board;
    protected boolean[][] validMoves;
    protected int moveCount;
    protected int lastMoved;
    protected boolean isCheckingKing;

    public Piece(PieceColor color, Position position, Board board, char icon, String notationSymbol){
        this.color = color;
        this.position = position;
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        this.moveCount = 0;
        this.lastMoved = 0;
        this.isCheckingKing = false;
    }

    public Piece(PieceColor color, Position position, Board board, char icon, String notationSymbol, int moveCount, int lastMoved){
        this.color = color;
        this.position = position;
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        this.moveCount = moveCount;
        this.lastMoved = lastMoved;
        this.isCheckingKing = false;
    }

    public void updatePosition(int row, int col, int boardMoveCount){
        position.setPosition(row, col);
        moveCount++;
        lastMoved = boardMoveCount;
    }

    public abstract void calculateValidMoves();

    public boolean isMoveValid(Position position){
        return validMoves[position.getRow()][position.getCol()];
    }

    public boolean hasValidMoves(){
        calculateValidMoves();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(validMoves[i][j]){
                    return true;
                }
            }
        }
        return false;
    }

    public PieceColor getColor(){
        return color;
    }

    public int getMoveCount(){
        return moveCount;
    }

    public int getLastMove(){
        return lastMoved;
    }

    public String getNotationSymbol(){
        return notationSymbol;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
