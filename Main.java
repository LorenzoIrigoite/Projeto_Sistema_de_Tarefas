public class Main {
 public static void main(String[] args) {

   DataBase.criarTabela();
  
    Task t1 = new Task(1, "Lorenzo", "23/23/23", 3);
    Task t2 = new Task(2, "Gabriela", "11/10/2006", 2);
    Task t3 = new Task(3, "loriela", "02/10/2006", 1);

    TaskManeger sistema = new TaskManeger();


    sistema.addTask(t1);
    sistema.addTask(t2);
    sistema.addTask(t3);
    
    sistema.listTasks();
    sistema.removeTask(3);
    sistema.removeTask(2);
    sistema.listTasks();

    sistema.updateDoneTask(t1, true);
    sistema.updateDateTask(t1, "25/03/2026");
    sistema.listTasks();
 }   
}
