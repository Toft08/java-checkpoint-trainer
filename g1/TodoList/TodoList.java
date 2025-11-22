public class TodoList {
    private Task[] tasks;
    private int count = 0;
    private int capacity;

    public TodoList(int capacity) {
        this.capacity = capacity;
        tasks = new Task[capacity]; // create fixed-size list
    }

    public void addTask(String description) {
        if (count < capacity) {
            tasks[count] = new Task(description);
            count++;
        }
    }

    public void setStatus(int index, TaskStatus status) {
        if (index >= 0 && index < count) {
            tasks[index].setStatus(status);
        }
    }

    public void setDescription(int index, String newDescription) {
        if (index >= 0 && index < count) {
            tasks[index].setDescription(newDescription);
        }
    }

    public void displayTasks() {
        System.out.println("Tasks:");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-33s | %s%n",
                tasks[i].getDescription(), tasks[i].getStatus());
        }
    }
}