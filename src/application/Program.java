package application;

import entities.Match;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args){

        try(Scanner sc = new Scanner(System.in)){

            ArrayList<String> inputHistory = Match.playMatch(sc);

            int answer = 0;
            while(answer != 3){
                System.out.println("1- Play again");
                System.out.println("2- Show match replay");
                System.out.println("3- Exit game");
                System.out.println("Answer (1/2/3): ");

                try {
                    answer = sc.nextInt();
                    switch(answer){
                        case 1 -> {
                            //consuming newline
                            sc.nextLine();
                            inputHistory = Match.playMatch(sc);
                        }
                        case 2 -> Match.replayMatch(inputHistory);
                        case 3 -> {}
                        default -> throw new InputMismatchException("Invalid input.");
                    }
                }
                catch (InputMismatchException e){
                    System.out.println(e.getMessage());
                    //consuming newline
                    sc.nextLine();
                }
            }

        }
    }
}
