package diablo.ui;

import diablo.exception.DiabloException;
import diablo.task.Deadline;
import diablo.task.Event;
import diablo.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {
    @Test
    public void markTaskTest() {
        TaskList taskList = new TaskList();
        Task task1 = new Deadline("do homework", "2025-08-29");
        Task task2 = new Event("do chores", "2025-08-24", "2025-08-25");
        taskList.addTask(task1);
        taskList.addTask(task2);
        Task task3 = new Deadline("do homework", "2025-08-29");
        task3.complete();
        try {
            taskList.mark(1);
        } catch (DiabloException e) {}

        String output = taskList.formatForUi().get(0);

        assertEquals(task3.toString(), output);

    }

    @Test
    public void markTestWithWrongIndexTest() {
        TaskList taskList = new TaskList();
        Task task1 = new Deadline("do homework", "2025-08-29");
        Task task2 = new Event("do chores", "2025-08-24", "2025-08-25");
        taskList.addTask(task1);
        taskList.addTask(task2);

        try {
            taskList.mark(3);
            Assertions.fail();
        } catch (DiabloException e) {
            assertEquals("Invalid task number! Check your tasks again.", e.getMessage());
        }


    }

}
