import java.util.ArrayList;

public class Task {
    private int id;
    private String name;
    private String date;
    private double lvl;
    private boolean isDone;

    public Task(int id, String name, String date, double lvl){
        this.id = id;
        this.name = name;
        this.date = date;
        this.lvl = lvl;
        this.isDone = false;
    }

    public String getDate() {
        return date;
    }

    public double getLvl() {
        return lvl;
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

    public void setLvl(double lvl) {
        this.lvl = lvl;
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
        return "Task ID: " + this.id + " | Task: " + this.name + " | Date: " + this.date + " | Level: " + this.lvl + " | Done: " + this.isDone;
    }
    
}
