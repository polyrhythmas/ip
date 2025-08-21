import java.util.ArrayList;
import java.util.Scanner; // Import Scanner class

public class Diablo {
    public static void main(String[] args) {
        // Preliminaries
        String botName = "Diablo";
        String greeting = "\tHello! I'm " + botName + "\n\tWhat can I do for you?";
        String exit = "\tBye. Hope to see you again soon!";
        ArrayList<String> list = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);

        // Start
        printHorizontalLine();
        System.out.println(greeting);
        printHorizontalLine();
        boolean finished = false;
        while (!finished) {
            String userInput = scanner.nextLine();
            printHorizontalLine();
            if (userInput.equals("bye")) {
                finished = true;
            } else if (userInput.equals("list")) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("\t" + (i + 1) + ": " + list.get(i));
                }
                printHorizontalLine();
            } else {
                System.out.println("\tadded: " + userInput);
                list.add(userInput);
                printHorizontalLine();
            }
        }
        System.out.println(exit);
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        char line = '-';
        int lineLength = 50;

        System.out.print("\t");
        for (int i = 0; i < lineLength; i++) {
            System.out.print(line);
        }

        System.out.println();
    }
}
