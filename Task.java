import java.util.Scanner;
public class Task {
    private static int nextId = 1;
    private int id;
    private String name;
    private String date;
    private String horario;
    private boolean isDone;
    private Cliente owner;
    public Task(int id, String name, String date, String horario){
        this.id = id;
        this.name = name;
        this.date = date;
        this.horario = horario;
        this.isDone = false;
        this.owner = null;
    }
    public Task(String name, String date, String horario, Cliente owner){
        this.id = nextId++;
        this.name = name;
        this.date = date;
        this.horario = horario;
        this.isDone = false;
        this.owner = owner;
    }

    public static Task personalTask(Cliente owner){
        Scanner t = new Scanner(System.in);
        return personalTask(owner, t);
    }

    public static Task personalTask(Cliente owner, Scanner t){
        if (owner == null) {
            System.out.println("Erro: Cliente não pode ser nulo.");
            return null;
        }
        System.out.println("Digite o nome da tarefa:");
        String name = t.nextLine();
        System.out.println("Digite a data da tarefa (formato: DD-MM-YYYY):");
        String date = t.nextLine();
        System.out.println("Digite o horário da tarefa (formato: HH:MM):");
        String horario = t.nextLine();
        
        Task task = new Task(name, date, horario, owner);
        owner.adicionarTarefa(task);
        
        TaskManeger tm = new TaskManeger();
        tm.addTask(task);
  
        ClienteManager cm = new ClienteManager();
        cm.vincularTarefaAoCliente(owner.getId(), task);
        
       
        return task;
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

    public Cliente getOwner() {
        return owner;
    }

    public void setOwner(Cliente owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString(){
        return "Task ID: " + this.id + " | Task: " + this.name + " | Date: " + this.date + " | Horario: " + this.horario + " | Done: " + this.isDone;
    }
    
}
