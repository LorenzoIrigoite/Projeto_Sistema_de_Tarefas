import java.util.ArrayList;

public class TaskManeger {

    private ArrayList<Task> lista = new ArrayList<>();

    public void addTask(Task t) {
        if (t != null) {
            lista.add(t);
        }
    }
}

