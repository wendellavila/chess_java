package entities;

//import entities.enums.PieceColor;
import entities.exceptions.CheckmateException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {

    //regex used by movePieces
    private static final Pattern inputPattern = Pattern.compile("([a-hA-H])(\\d)\\s?-?([a-hA-H])(\\d)\\s?-?([QqNnRrBb]?)");

    public static ArrayList<String> playPVPMatch(Scanner sc){

        int turnCount = 1;
        Board board = new Board(sc);
        System.out.println(board);

        Matcher matcher;

        while(true){
            try {
                String currentColor = board.getMoveCount() % 2 == 0 ? "Whites" : "Blacks";
                System.out.println("Turn " + turnCount + " - " + currentColor + " play\nInput: ");

                String input = sc.nextLine();
                matcher = inputPattern.matcher(input);

                if(matcher.matches()){
                    Position origin = new Position();
                    Position destination = new Position();

                    origin.setRow(Integer.parseInt(matcher.group(2)) - 1);
                    destination.setRow(Integer.parseInt(matcher.group(4)) - 1);
                    //converting letters a-h to integers 0-7
                    origin.setCol((int) matcher.group(1).toLowerCase().charAt(0) - (int)'a');
                    destination.setCol((int) matcher.group(3).toLowerCase().charAt(0) - (int)'a');

                    if(origin.getRow() < 0 || origin.getRow() > 7 || destination.getRow() < 0 || destination.getRow() > 7 ||
                            origin.getCol() < 0 || origin.getCol() > 7 || destination.getCol() < 0 || destination.getCol() > 7){
                        throw new InvalidNotationException(input);
                    }
                    else {
                        if(matcher.groupCount() > 4 && matcher.group(5) != null && !matcher.group(5).isEmpty()){
                            board.movePieces(origin, destination, matcher.group(5));
                        }
                        else {
                            board.movePieces(origin, destination, null);
                        }
                    }
                }
                else {
                    throw new InvalidNotationException(input);
                }
                turnCount = board.getMoveCount() % 2 == 0 ? turnCount + 1 : turnCount;
                System.out.println("\n\n" + board);
            }
            catch (InvalidNotationException | InvalidMoveException e){
                System.out.println(e.getMessage());
            }
            catch (CheckmateException e){
                System.out.println("\n\n" + board);
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

        Matcher matcher;

        for(String input : inputHistory){

            matcher = inputPattern.matcher(input);
            //only matching inputs are added to inputHistory; no need for checking.
            matcher.matches();
            try {
                Position origin = new Position();
                Position destination = new Position();

                origin.setRow(Integer.parseInt(matcher.group(2)) - 1);
                destination.setRow(Integer.parseInt(matcher.group(4)) - 1);
                //converting letters a-h to integers 0-7
                origin.setCol((int) matcher.group(1).toLowerCase().charAt(0) - (int) 'a');
                destination.setCol((int) matcher.group(3).toLowerCase().charAt(0) - (int) 'a');

                if(matcher.groupCount() > 4 && matcher.group(5) != null && !matcher.group(5).isEmpty()){
                    board.movePieces(origin, destination, matcher.group(5));
                }
                else {
                    board.movePieces(origin, destination, null);
                }

                System.out.println(board);
                Thread.sleep(1500);
            }
            catch (InvalidNotationException | InvalidMoveException e){
                System.out.println(e.getMessage());
            }
            catch (CheckmateException e){
                System.out.println("\n\n" + board);
                System.out.println(e.getMessage());
                break;
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public static void playPVEMatch(PieceColor color){
//        //
//    }
}
