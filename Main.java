public class Main {
 public static void main(String[] args) {

   DataBase.criarTabela();

    Task t1 = new Task("Lorenzo", "23/23/23", 3);
    Task t2 = new Task("Gabriela", "11/10/2006", 2);
    Task t3 = new Task("loriela", "02/10/2006", 1);

    TaskManeger sistema = new TaskManeger();
      sistema.addTask(t1);
      sistema.addTask(t2);
      sistema.addTask(t3);
      sistema.removeTask(2);
    
      sistema.listTasks();
     
 }   
}
