package diablo.ui;

import diablo.task.Deadline;
import diablo.task.Event;
import diablo.task.Task;
import diablo.task.ToDo;
import diablo.exception.DiabloException;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Contains and manages the task list for the chatbot.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task A Task object.
     * @return String representation of the task.
     */
    public String addTask(Task task) {
        tasks.add(task);
        return task.toString();
    }


    /**
     * Marks a task as done. Will throw a DiabloException if the list does not have a task with the task number.
     *
     * @param taskNumber The position of the task in the list.
     * @return String representation of the completed task.
     * @throws DiabloException If the list does not have a task with the task number.
     */
    public String mark(int taskNumber) throws DiabloException {
        if (taskNumber > tasks.size() || taskNumber <= 0) {
            throw new DiabloException("Invalid task number! Check your tasks again.");
        } else {
            Task toComplete = tasks.get(taskNumber - 1);
            toComplete.complete();
            return toComplete.toString();
        }
    }


    /**
     * Deletes a task from the list. Will throw a DiabloException if the list does not have a task with the task number.
     *
     * @param taskNumber The position of the task in the list.
     * @return String representation of the deleted task.
     * @throws DiabloException If the list does not have a task with the task number.
     */
    public String delete(int taskNumber) throws DiabloException {
        if (taskNumber > tasks.size() || taskNumber <= 0) {
            throw new DiabloException("Invalid task number! Check your tasks again.");
        } else {
            Task toDelete = tasks.get(taskNumber - 1);
            tasks.remove(taskNumber - 1);
            return toDelete.toString();
        }
    }


    /**
     * Filters tasks based on their description and a given key, and returns an ArrayList of filtered tasks as strings.
     *
     * @param key String to find in each task description.
     * @return ArrayList of filtered tasks as strings.
     */
    public ArrayList<String> filterAndFormatForUi(String key) {
        ArrayList<String> outputList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (Arrays.asList(tasks.get(i).getDescription().split(" ")).contains(key)) {
                outputList.add(tasks.get(i).toString());
            }
        }
        return outputList;
    }


    /**
     * Formats the current task list for the Ui to receive and print out.
     *
     * @return ArrayList of string representations of the tasks.
     */
    public ArrayList<String> formatForUi() {
        ArrayList<String> outputList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            outputList.add(tasks.get(i).toString());
        }
        return outputList;
    }

    
    public ArrayList<String> formatForStorage() {
        ArrayList<String> listOfStrings = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
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
}
