package diablo.task;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }


    /**
     * Gets the type of task.
     *
     * @return Capital letter "T" which represents a todo task.
     */
    @Override
    public String getType() {
        return "T";
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
