import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner; // Import Scanner class

public class Diablo {
    public static void main(String[] args) throws DukeException {
        // Preliminaries
        String botName = "Diablo";
        String greeting = "\tHello! I'm " + botName + "\n\tWhat can I do for you?";
        String exit = "\tBye. Hope to see you again soon!";
        ArrayList<Task> list = new ArrayList<>();
        String filePath = "src/main/data/diablo.txt";


        // File reading
        try {
            // Check for file existence
            File diabloFile = new File(filePath);
            if (diabloFile.createNewFile()) {
            } else {
                readDiablo(filePath, list);
            }
        } catch (IOException e) {
            System.out.println("\tAn error occured when reading data. Try again!");
        }



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
            switch (firstWord) {
                case "bye":
                    finished = true;
                    continue;
                case "list":
                    if (list.isEmpty()) {
                        System.out.println("\tThere are no list items yet!");
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println("\t" + (i + 1) + ":" + list.get(i));
                        }
                    }
                    break;
                case "mark": {
                    int stringLen = userInput.length();
                    int taskNumber = Integer.parseInt(userInput.substring(stringLen - 1, stringLen));
                    Task task = list.get(taskNumber - 1);
                    task.complete();
                    System.out.println("\tNice! I've marked this task as done:\n\t\t" + task);
                    break;
                }
                case "delete": {
                    int stringLen = userInput.length();
                    int taskNumber = Integer.parseInt(userInput.substring(stringLen - 1, stringLen));
                    Task task = list.get(taskNumber - 1);
                    list.remove(taskNumber - 1);
                    System.out.println("\t Alright, I've removed this task:\n\t\t" + task);
                    break;
                }
                case "deadline":
                    try {
                        int startBy = userInput.indexOf("/by");
                        if (startBy == -1) {
                            String msg = "Please indicate a deadline!";
                            throw new DukeException(msg);
                        } else {
                            String description = userInput.substring(9, startBy - 1);
                            String day = userInput.substring(startBy + 4);
                            Task deadline = new Deadline(description, day);
                            addTask(deadline, list);
                            break;
                        }
                    } catch (DukeException e) {
                        System.out.println("\t" + e.getMessage());
                        break;
                    }
                case "todo":
                    try {
                        String noSpaces = userInput.replace(" ", "");
                        int todoLength = noSpaces.length();
                        if (todoLength < 5) {
                            String msg = "PLease include something you want to do!";
                            throw new DukeException(msg);
                        } else {
                            String description = userInput.substring(5);
                            Task todo = new ToDo(description);
                            addTask(todo, list);
                            break;
                        }
                    } catch (DukeException e) {
                        System.out.println("\t" + e.getMessage());
                        break;
                    }
                case "event":
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
                            addTask(event, list);
                            break;
                        }
                    } catch (DukeException e) {
                        System.out.println("\t" + e.getMessage());
                        break;
                    }
                default: {
                    System.out.println("\t" + "I don't know what that means!!!");
                    break;
                }
            }
            ArrayList<String> currentListFormatted = formatTasks(list);
            try {
                writeDiablo(currentListFormatted, filePath);
            } catch (IOException e) {
                System.out.println("\tAn error occured when reading data. Try again!");
            }

            printHorizontalLine();

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

    private static void addTask(Task task, ArrayList<Task> list) {
        list.add(task);
        System.out.println("\tGot it. I've added this task:\n\t\t");
        System.out.println("\tYou now have " + list.size() + " tasks in the list.");
    }

    private static void readDiablo(String filePath, ArrayList<Task> taskList) throws IOException{
        // Initialise string list
        ArrayList<String> listOfStrings = new ArrayList<>();

        // Load data
        BufferedReader bf = new BufferedReader(new FileReader(filePath));

        // Read first line as string
        String line = bf.readLine();

        // Read rest of strings until end of file
        while (line != null) {
            listOfStrings.add(line);
            line = bf.readLine();
        }

        // Close bufferreader object
        bf.close();

        // Add tasks from strings
        for (int i = 0; i < listOfStrings.size(); i++) {
            String[] taskDetails = listOfStrings.get(i).split("___");

            // Add specific task from initial element
            switch (taskDetails[0]) {
                case "T":
                    Task toDo = new ToDo(taskDetails[2]);
                    taskList.add(toDo);
                    break;

                case "D":
                    Task deadline = new Deadline(taskDetails[2], taskDetails[3]);
                    taskList.add(deadline);
                    break;

                case "E":
                    Task event = new Event(taskDetails[2], taskDetails[3], taskDetails[4]);
                    taskList.add(event);
                    break;
            }

            // Completes task if it is already done
            if (taskDetails[1].equals("1")) {
                taskList.get(i).complete();
            }

        }
    }

    private static ArrayList<String> formatTasks(ArrayList<Task> taskList) {
        ArrayList<String> listOfStrings = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            String status = task.getStatusIcon();
            String completed = "";

            if (status.equals(" ")) {
                completed = "0";
            } else {
                completed = "1";
            }

            String result = "";
            switch (task.getType()) {
                case "T": {
                    ToDo todo = (ToDo) task;
                    String[] taskDetails = {todo.getType(), completed, todo.getDescription()};
                    result = String.join("___", taskDetails);
                    break;
                }

                case "D": {
                    Deadline deadline = (Deadline) task;
                    String[] taskDetails = {deadline.getType(),
                                            completed,
                                            deadline.getDescription(),
                                            deadline.getByDate()};
                    result = String.join("___", taskDetails);
                    break;
                }

                case "E": {
                    Event event = (Event) task;
                    String[] taskDetails = {event.getType(),
                                            completed,
                                            event.getDescription(),
                                            event.getFromDate(),
                                            event.getToDate()};
                    result = String.join("___", taskDetails);
                    break;
                }


            }

            listOfStrings.add(result);

        }
        return listOfStrings;

    }

    private static void writeDiablo(ArrayList<String> listOfStrings, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < listOfStrings.size(); i++) {
            writer.append(listOfStrings.get(i));
            writer.append("\n");
        }
        writer.close();
    }
}
