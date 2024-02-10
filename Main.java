
//Малинин Андрей

import java.util.List;
import java.util.Scanner;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        AbstractGame ag = new GameNumber();
        ag.start(5, 3);
        System.out.println("Welcome to the Bulls and Cows game!");

        Scanner scanner = new Scanner(System.in);

        while (ag.getGameStatus().equals(GameStatus.START)) {
            System.out.println("Enter your guess:");
            String userGuess = scanner.nextLine();
            Answer answer = ag.inputValue(userGuess);
            System.out.println(answer);
        }

        if (ag.getGameStatus().equals(GameStatus.WIN)) {
            System.out.println("Congratulations! You win!");
        } else if (ag.getGameStatus().equals(GameStatus.LOOSE)) {
            System.out.println("Sorry, you lose!");
        } else {
            System.out.println("Unknown game status.");
        }

        System.out.println("Do you want to see the game history? (Y/N)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("Y")) {
            List<String> history = ag.getGameHistory();
            System.out.println("Game History:");
            for (String entry : history) {
                System.out.println(entry);
            }
        }

        System.out.println("Do you want to play again? (Y/N)");
        response = scanner.nextLine();
        if (response.equalsIgnoreCase("Y")) {
            ag.restart();
            System.out.println("\nStarting a new game...\n");
            main(args); // Рекурсивный вызов для начала новой игры
        } else {
            System.out.println("Thank you for playing!");
        }

        scanner.close();
    }
}
