import java.util.*;

public class Task {
    private String name;
    private String date;
    private double lvl;

    public Task(String name, String date, double lvl){
        this.name = name;
        this.date = date;
        this.lvl = lvl;
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

    public void setDate(String date) {
        this.date = date;
    }

    public void setLvl(double lvl) {
        this.lvl = lvl;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
