public class Task {
    private int id;
    private String name;
    private String date;
    private String horario;
    private boolean isDone;

    public Task(int id, String name, String date, String horario){
        this.id = id;
        this.name = name;
        this.date = date;
        this.horario = horario;
        this.isDone = false;
    }

    public String getDate() {
        return date;
    }

    public String getHorario() {
        return horario;
    }

    public String getName() {
        return name;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        return "Task ID: " + this.id + " | Task: " + this.name + " | Date: " + this.date + " | Horario: " + this.horario + " | Done: " + this.isDone;
    }
    
}
