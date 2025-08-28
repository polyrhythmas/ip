package diablo.ui;

import diablo.task.Deadline;
import diablo.task.Event;
import diablo.task.Task;
import diablo.task.ToDo;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;

        // File reading
        try {
            // Check for file existence
            File diabloFile = new File(filePath);
            diabloFile.createNewFile();
        } catch (IOException e) {
            System.out.println("\tAn error occured when reading data. Try again!");
        }
    }


    public ArrayList<Task> readDiablo() throws IOException {
        // Initialise string list
        ArrayList<String> listOfStrings = new ArrayList<>();

        // Initialise output task list
        ArrayList<Task> taskList = new ArrayList<>();

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

        return taskList;
    }

    public void writeDiablo(ArrayList<String> listOfStrings, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < listOfStrings.size(); i++) {
            writer.append(listOfStrings.get(i));
            writer.append("\n");
        }
        writer.close();
    }
}
