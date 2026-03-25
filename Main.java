public class Main {
 public static void main(String[] args) {
    System.out.println("Iniciando programa...");
    System.out.println("==============================\n");

    try {
        // Criar tabelas (recriar tabela tarefas para atualizar estrutura)
        DataBase.recriarTabelaTarefas();
        DataBase.criarTabelaClientes();
        DataBase.criarTabelaClienteTarefas();
        TaskManeger tm = new TaskManeger();
        ClienteManager cm = new ClienteManager();
        
        System.out.println("Tabelas criadas/atualizadas e Sistemas Iniciados!\n");

        
        Task t1 = new Task(1, "lavar roupa", "23/23/23", "14:30");
        Task t2 = new Task(2, "Reuniao do trabalho", "23/12/2026", "09:00");

        tm.addTask(t1);
        tm.addTask(t2);

        Cliente Lorenzo = new Cliente("Lorenzo", "lorenzo@mail.com", "(11) 99999-0001");
        
      cm.salvarCliente(Lorenzo);

        


    } catch (Exception e) {
        System.out.println("ERRO no programa: " + e.getMessage());
        e.printStackTrace(); //metodo para ajudar a encontrar os bugd do sql, usado no trycath.
    }

 }

}
