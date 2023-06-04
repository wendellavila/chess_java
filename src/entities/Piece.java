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

    public Piece(PieceColor color, int row, int col, Board board, char icon, String notationSymbol){
        this.color = color;
        this.position = new Position(row, col);
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        this.moveCount = 0;
        this.lastMoved = 0;
    }

    public Piece(PieceColor color, int row, int col, Board board, char icon, String notationSymbol, int moveCount, int lastMoved){
        this.color = color;
        this.position = new Position(row, col);
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        this.moveCount = moveCount;
        this.lastMoved = lastMoved;
    }

    public void updatePosition(int row, int col, int boardMoveCount){
        position.setPosition(row, col);
        moveCount++;
        lastMoved = boardMoveCount;
    }

    public abstract void calculateValidMoves();

    public boolean isMoveValid(int row, int col){
        return validMoves[row][col];
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
