import java.util.Scanner; // Import Scanner class

public class Diablo {
    public static void main(String[] args) {
        // Preliminaries
        String botName = "Diablo";
        String greeting = "\tHello! I'm " + botName + "\n\tWhat can I do for you?";
        String exit = "\tBye. Hope to see you again soon!";
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
            } else {
                System.out.println("\t" + userInput);
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
