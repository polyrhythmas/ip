import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner SCANNER;
    private final String BOT_NAME = "Diablo";
    private final String GREETING = "\tHello! I'm " + BOT_NAME + "\n\tWhat can I do for you?";
    private final String EXIT = "\tBye. Hope to see you again soon!";

    public Ui() {
        this.SCANNER = new Scanner(System.in);
    }

    public String nextInput() {
        return this.SCANNER.nextLine();
    }

    public void greetUser() {
        printHorizontalLine();
        System.out.println(GREETING);
        printHorizontalLine();
    }

    public void sayBye() {
        printHorizontalLine();
        System.out.println(EXIT);
        printHorizontalLine();
    }

    public void showList(ArrayList<String> tasks) {
        printHorizontalLine();
        if (tasks.isEmpty()) {
            System.out.println("\tThere are no list items yet!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("\t" + (i + 1) + ":" + tasks.get(i));
            }
        }
        printHorizontalLine();
    }

    public void printErrorMessage(DukeException e) {
        printHorizontalLine();
        System.out.println("\t" + e.getMessage());
        printHorizontalLine();
    }

    public void invalidInputMessage() {
        printHorizontalLine();
        System.out.println("\t" + "I don't know what that means!!!");
        printHorizontalLine();
    }

    public void addTask(String task) {
        printHorizontalLine();
        System.out.println("\tGot it. I've added this task:\n\t\t" + task);
        printHorizontalLine();
    }

    public void markTask(String task) {
        printHorizontalLine();
        System.out.println("\tNice! I've marked this task as done:\n\t\t" + task);
        printHorizontalLine();
    }

    public void deleteTask(String task) {
        printHorizontalLine();
        System.out.println("\t Alright, I've removed this task:\n\t\t" + task);
        printHorizontalLine();
    }

    private void printHorizontalLine() {
        char line = '-';
        int lineLength = 50;

        System.out.print("\t");
        for (int i = 0; i < lineLength; i++) {
            System.out.print(line);
        }

        System.out.println();
    }
}
