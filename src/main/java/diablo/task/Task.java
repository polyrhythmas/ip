package diablo.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getType() {
        return "";
    }

    public String getDescription() {
        return this.description;
    }

    public void complete() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return String.format("[%1$s] %2$s", this.getStatusIcon(), this.description);
    }
}
