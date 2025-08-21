import java.util.ArrayList;
import java.util.Scanner; // Import Scanner class

public class Diablo {
    public static void main(String[] args) throws DukeException{
        // Preliminaries
        String botName = "Diablo";
        String greeting = "\tHello! I'm " + botName + "\n\tWhat can I do for you?";
        String exit = "\tBye. Hope to see you again soon!";
        ArrayList<Task> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Start
        printHorizontalLine();
        System.out.println(greeting);
        printHorizontalLine();
        boolean finished = false;
        while (!finished) {
            String userInput = scanner.nextLine();
            printHorizontalLine();
            String firstWord = userInput.split(" ")[0];
            if (userInput.equals("bye")) {
                finished = true;
            } else if (userInput.equals("list")) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println("\t" + (i + 1) + ":" + list.get(i));
                }
                printHorizontalLine();
            } else if (firstWord.equals("mark") || firstWord.equals("delete")) {
                int stringLen = userInput.length();
                int taskNumber = Integer.parseInt(userInput.substring(stringLen - 1, stringLen));
                Task task = list.get(taskNumber - 1);
                if (firstWord.equals("mark")) {
                    task.complete();
                    System.out.println("\tNice! I've marked this task as done:\n\t\t" + task);
                } else {
                    list.remove(taskNumber - 1);
                    System.out.println("\t ALright, I've removed this task:\n\t\t" + task);
                }

                printHorizontalLine();
            }
            else {
                if (firstWord.equals("deadline")) {
                    try {
                        int startBy = userInput.indexOf("/by");
                        if (startBy == -1) {
                            String msg = "Please indicate a deadline!";
                            throw new DukeException(msg);
                        } else {
                            String description = userInput.substring(9, startBy - 1);
                            String day = userInput.substring(startBy + 4);
                            Task deadline = new Deadline(description, day);
                            list.add(deadline);
                        }
                    } catch (DukeException e) {
                        System.out.println("\t" + e.getMessage());
                        printHorizontalLine();
                        continue;
                    }
                } else if (firstWord.equals("todo")) {
                    try {
                        String noSpaces = userInput.replace(" ", "");
                        int todoLength = noSpaces.length();
                        if (todoLength < 5) {
                            String msg = "PLease include something you want to do!";
                            throw new DukeException(msg);
                        } else {
                            String description = userInput.substring(5);
                            Task todo = new ToDo(description);
                            list.add(todo);
                        }
                    } catch (DukeException e) {
                        System.out.println("\t" + e.getMessage());
                        printHorizontalLine();
                        continue;
                    }
                } else if (firstWord.equals("event")) {
                    try {
                        int startFrom = userInput.indexOf("/from");
                        int startTo = userInput.indexOf("/to");
                        if (startFrom == -1) {
                            String msg = "Please include a start date!";
                            throw new DukeException(msg);
                        } else if (startTo == -1) {
                            String msg = "Please include an end date!";
                            throw new DukeException(msg);
                        } else {
                            String description = userInput.substring(6, startFrom - 1);
                            String from = userInput.substring(startFrom + 6, startTo - 1);
                            String to = userInput.substring(startTo + 4);
                            Task event = new Event(description, from, to);
                            list.add(event);
                        }
                    } catch (DukeException e) {
                        System.out.println("\t" + e.getMessage());
                        printHorizontalLine();
                        continue;
                    }
                } else {
                    System.out.println("\t" + "I don't know what that means!!!");
                    printHorizontalLine();
                    continue;
                }
                Task task = list.get(list.size() - 1);
                System.out.println("\tGot it. I've added this task:\n\t\t" + task);
                System.out.println("\tYou now have " + list.size() + " tasks in the list.");
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
