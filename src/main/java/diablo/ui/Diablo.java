package diablo.ui;

import diablo.task.Deadline;
import diablo.task.Event;
import diablo.task.Task;
import diablo.task.ToDo;
import diablo.exception.DiabloException;

import java.io.*;
import java.util.Scanner; // Import Scanner class


/**
 * Represents the main class for the Diablo chatbot.
 */
public class Diablo {
    private Storage storage;
    private TaskList taskList;

    public Diablo(String filePath) {
        this.storage = new Storage(filePath);
        try {
            this.taskList = new TaskList(storage.readDiablo());
        } catch (IOException e) {
            this.taskList = new TaskList();
        }

    }

    /**
     * Makes sense of information typed in by the user, and outputs a string for Diablo to message back.
     * @param input String input by the user.
     * @return String message of Diablo response.
     */
    public String[] getOutput(String input) {
        String[] parsedInput = Parser.parse(input);
        String inputType = parsedInput[0];
        switch (inputType) {
            case "bye":
                String byeMessage = "Bye. Hope to see you again soon!";
                return new String[] {"1", byeMessage};
            case "list": {
                String list = taskList.formatForWindow();
                return new String[] {"0", list};
            }
            case "mark": {
                try {
                    String taskMarkedMessage = taskList.mark(Integer.parseInt(parsedInput[1]));
                    return new String[] {"0", taskMarkedMessage};
                } catch (DiabloException e) {
                    return new String[] {"0", e.getMessage()};
                }
            }
            case "delete": {
                try {
                    String taskDeletedMessage = taskList.delete(Integer.parseInt(parsedInput[1]));
                    return new String[] {"0", taskDeletedMessage};
                } catch (DiabloException e) {
                    return new String[] {"0", e.getMessage()};
                }
            }
            case "find": {
                String list = taskList.filterAndFormatForWindow(parsedInput[1]);
                return new String[] {"0", list};
            }
            case "deadline":
                try {
                    if (parsedInput[1].equals("-1")) {
                        throw new DiabloException(parsedInput[2]);
                    } else {
                        Task deadline = new Deadline(parsedInput[1], parsedInput[2]);
                        return new String[] {"0", taskList.addTask(deadline)};
                    }
                } catch (DiabloException e) {
                    return new String[] {"0", e.getMessage()};
                }
            case "todo":
                try {
                    if (parsedInput[1].equals("-1")) {
                        throw new DiabloException(parsedInput[2]);
                    } else {
                        Task todo = new ToDo(parsedInput[1]);
                        return new String[] {"0", taskList.addTask(todo)};
                    }
                } catch (DiabloException e) {
                    return new String[] {"0", e.getMessage()};
                }
            case "event":
                try {
                    if (parsedInput[1].equals("-1")) {
                        throw new DiabloException(parsedInput[2]);
                    } else {
                        Task event = new Event(parsedInput[1], parsedInput[2], parsedInput[3]);
                        return new String[] {"0", taskList.addTask(event)};
                    }
                } catch (DiabloException e) {
                    return new String[] {"0", e.getMessage()};
                }
            default: {
                String unknownMessage = "I don't know what that means!!!";
                return new String[] {"0", unknownMessage};
            }
        }

    }

    /**
     * Writes current task list into diablo.txt for storage.
     */
    public void addToStorage() {
        try {
            storage.writeDiablo(taskList.formatForStorage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
