public class Diablo {
    public static void main(String[] args) {
        String botName = "Diablo";
        String greeting = "Hello! I'm " + botName + "\nWhat can I do for you?";
        String exit = "Bye. Hope to see you again soon!";
        
        printHorizontalLine();
        System.out.println(greeting);
        printHorizontalLine();
        System.out.println(exit);
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        char line = '-';
        int lineLength = 50;

        for (int i = 0; i < lineLength; i++) {
            System.out.print(line);
        }

        System.out.println();
    }
}
