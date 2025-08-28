import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner; // Import Scanner class

public class Diablo {
    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    public Diablo(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.taskList = new TaskList(storage.readDiablo());
        } catch (IOException e) {
            this.taskList = new TaskList();
        }

    }


    public void run() {
        // Preliminaries

        String filePath = "src/main/data/diablo.txt";

        Scanner scanner = new Scanner(System.in);

        // Start
        ui.greetUser();
        boolean finished = false;
        while (!finished) {
            String userInput = ui.nextInput();
            String[] parsedInput = Parser.parse(userInput);
            String inputType = parsedInput[0];
            switch (inputType) {
                case "bye":
                    finished = true;
                    continue;
                case "list": {
                    ui.showList(taskList.formatForUi());
                    break;
                }
                case "mark": {
                    try {
                        ui.markTask(taskList.mark(Integer.parseInt(parsedInput[1])));
                    } catch (DukeException e) {
                        ui.printErrorMessage(e);
                    }
                    break;
                }
                case "delete": {
                    try {
                        ui.deleteTask(taskList.delete(Integer.parseInt(parsedInput[1])));
                    } catch (DukeException e) {
                        ui.printErrorMessage(e);
                    }
                    break;
                }
                case "deadline":
                    try {
                        if (parsedInput[1].equals("-1")) {
                            throw new DukeException(parsedInput[2]);
                        } else {
                            Task deadline = new Deadline(parsedInput[1], parsedInput[2]);
                            ui.addTask(taskList.addTask(deadline));
                            break;
                        }
                    } catch (DukeException e) {
                        ui.printErrorMessage(e);
                        break;
                    }
                case "todo":
                    try {
                        if (parsedInput[1].equals("-1")) {
                            throw new DukeException(parsedInput[2]);
                        } else {
                            Task todo = new ToDo(parsedInput[1]);
                            ui.addTask(taskList.addTask(todo));
                            break;
                        }
                    } catch (DukeException e) {
                        ui.printErrorMessage(e);
                        break;
                    }
                case "event":
                    try {
                        if (parsedInput[1].equals("-1")) {
                            throw new DukeException(parsedInput[2]);
                        } else {
                            Task event = new Event(parsedInput[1], parsedInput[2], parsedInput[3]);
                            ui.addTask(taskList.addTask(event));
                            break;
                        }
                    } catch (DukeException e) {
                        System.out.println("\t" + e.getMessage());
                        break;
                    }
                default: {
                    ui.invalidInputMessage();
                    break;
                }
            }

            try {
                storage.writeDiablo(taskList.formatForStorage(), filePath);
            } catch (IOException e) {
                System.out.println("\tAn error occured when reading data. Try again!");
            }

        }
        ui.sayBye();
    }

    public static void main(String[] args) {
        new Diablo("src/main/data/diablo.txt").run();
    }

}
