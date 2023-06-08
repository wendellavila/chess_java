package entities;

//import entities.enums.PieceColor;
import entities.enums.PieceColor;
import entities.exceptions.GameOverException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;
import entities.utils.ANSICodes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {

    // regex for moves
    private static final Pattern inputPattern = Pattern.compile("([a-hA-H])(\\d)\\s?-?([a-hA-H])(\\d)\\s?-?([QqNnRrBb]?)");
    // regex for highlighting piece's available moves
    private static final Pattern highlightPattern = Pattern.compile("tip\\s?-?([a-hA-H])(\\d)");

    public static ArrayList<String> playMatch(Scanner sc){

        int turnCount = 1;
        Board board = new Board(sc);
        System.out.println(board);

        Matcher inputMatcher, highlightMatcher;

        while(true){
            try {
                PieceColor currentColor = board.getMoveCount() % 2 == 0 ? PieceColor.WHITE : PieceColor.BLACK;
                PieceColor oppositeColor = currentColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

                System.out.println("Turn " + turnCount + " - " + currentColor + " play");
                if((currentColor == PieceColor.WHITE && board.isWhiteInCheck()) || (currentColor == PieceColor.BLACK && board.isBlackInCheck())){
                    System.out.println(ANSICodes.ANSI_YELLOW + "Warning: Your King is in check!" + ANSICodes.ANSI_RESET);
                }
                System.out.print("Input: ");

                String input = sc.nextLine();
                inputMatcher = inputPattern.matcher(input);
                highlightMatcher = highlightPattern.matcher(input);

                if(input.equals("draw")){
                    System.out.println(currentColor + " proposed a draw.\nAccept? (y/n): ");
                    while(true){
                        String answer = sc.nextLine().toLowerCase();
                        if(answer.equals("y")){
                            board.addToLatestPlays(
                                    new NotationEntry("½-½", PieceColor.NONE, "½")
                            );
                            board.addToInputHistory(input);
                            throw new GameOverException("Draw by agreement!");
                        }
                        else if(answer.equals("n")){
                            break;
                        }
                    }

                }
                else if(input.equals("resign")){
                    board.addToInputHistory(input);
                    board.addToLatestPlays(
                            new NotationEntry(currentColor == PieceColor.WHITE ? "0-1" : "1-0", oppositeColor, "♚")
                    );
                    throw new GameOverException(currentColor + " resigns. " + oppositeColor + " wins!");
                }
                else if(highlightMatcher.matches()){
                    Position origin = new Position();

                    origin.setRow(Integer.parseInt(highlightMatcher.group(2)) - 1);
                    //converting letters a-h to integers 0-7
                    origin.setCol((int) highlightMatcher.group(1).toLowerCase().charAt(0) - (int)'a');

                    System.out.println(board.highlightPiece(origin));
                }
                else if(inputMatcher.matches()){
                    Position origin = new Position();
                    Position destination = new Position();

                    origin.setRow(Integer.parseInt(inputMatcher.group(2)) - 1);
                    destination.setRow(Integer.parseInt(inputMatcher.group(4)) - 1);
                    //converting letters a-h to integers 0-7
                    origin.setCol((int) inputMatcher.group(1).toLowerCase().charAt(0) - (int)'a');
                    destination.setCol((int) inputMatcher.group(3).toLowerCase().charAt(0) - (int)'a');

                    if(origin.getRow() < 0 || origin.getRow() > 7 || destination.getRow() < 0 || destination.getRow() > 7 ||
                            origin.getCol() < 0 || origin.getCol() > 7 || destination.getCol() < 0 || destination.getCol() > 7){
                        throw new InvalidNotationException(input);
                    }
                    else {
                        if(inputMatcher.groupCount() > 4 && inputMatcher.group(5) != null && !inputMatcher.group(5).isEmpty()){
                            board.movePieces(origin, destination, inputMatcher.group(5));
                        }
                        else {
                            board.movePieces(origin, destination, null);
                        }
                        turnCount = board.getMoveCount() % 2 == 0 ? turnCount + 1 : turnCount;
                        System.out.println(board);
                    }
                }
                else {
                    throw new InvalidNotationException(input);
                }
            }
            catch (InvalidNotationException | InvalidMoveException e){
                System.out.println(e.getMessage());
            }
            catch (GameOverException e){
                System.out.println(board);
                System.out.println(e.getMessage());
                break;
            }
        }
        return board.getInputHistory();
    }

    public static void replayMatch(ArrayList<String> inputHistory){
        Board board = new Board(null);

        if(!inputHistory.isEmpty()){
            System.out.println(board);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("No moves in input history.");
            return;
        }

        Matcher inputMatcher;

        for(String input : inputHistory){

            inputMatcher = inputPattern.matcher(input);

            try {
                if(input.equals("draw")){
                    board.addToLatestPlays(
                        new NotationEntry("½-½", PieceColor.NONE, "½")
                    );
                    throw new GameOverException("Draw by agreement!");
                }
                else if(input.equals("resign")){
                    PieceColor currentColor = board.getMoveCount() % 2 == 0 ? PieceColor.WHITE : PieceColor.BLACK;
                    PieceColor oppositeColor = currentColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
                    board.addToLatestPlays(
                            new NotationEntry(currentColor == PieceColor.WHITE ? "(0-1)" : "(1-0)", oppositeColor, "♚")
                    );
                    throw new GameOverException(currentColor + " resigns. " + oppositeColor + " wins!");
                }
                else if(inputMatcher.matches()){

                    Position origin = new Position();
                    Position destination = new Position();

                    origin.setRow(Integer.parseInt(inputMatcher.group(2)) - 1);
                    destination.setRow(Integer.parseInt(inputMatcher.group(4)) - 1);
                    //converting letters a-h to integers 0-7
                    origin.setCol((int) inputMatcher.group(1).toLowerCase().charAt(0) - (int) 'a');
                    destination.setCol((int) inputMatcher.group(3).toLowerCase().charAt(0) - (int) 'a');

                    if (inputMatcher.groupCount() > 4 && inputMatcher.group(5) != null && !inputMatcher.group(5).isEmpty()) {
                        board.movePieces(origin, destination, inputMatcher.group(5));
                    } else {
                        board.movePieces(origin, destination, null);
                    }
                    System.out.println(board);
                    Thread.sleep(1500);
                }
            } catch (InvalidNotationException | InvalidMoveException e) {
                System.out.println(e.getMessage());
            } catch (GameOverException e) {
                System.out.println(board);
                System.out.println(e.getMessage());
                break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
