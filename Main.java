public class Main {
 public static void main(String[] args) {
    System.out.println("Iniciando programa...");
    System.out.println("==============================\n");

    try {
        // Criar tabelas (recriar tabela tarefas para atualizar estrutura)
        DataBase.recriarTabelaTarefas();
        DataBase.criarTabelaClientes();
        DataBase.criarTabelaClienteTarefas();

        System.out.println("Tabelas criadas/atualizadas com sucesso!\n");

        // Criar algumas tarefas
        Task t1 = new Task(1, "lavar roupa", "23/23/23", "14:30");
        Task t2 = new Task(2, "Reuniao do trabalho", "23/12/2026", "09:00");

        System.out.println("Tarefas criadas...");

        // Salvar tarefas no banco
        TaskManeger tm = new TaskManeger();
        tm.addTask(t1);
        tm.addTask(t2);

        System.out.println("Tarefas salvas no banco...\n");

        ClienteManager clienteManager = new ClienteManager();

        // Criar um cliente simples
        Cliente cliente1 = new Cliente("João Silva", "joao@email.com", "(11) 99999-0001");

        System.out.println("Cliente criado...");

        // Salvar cliente no banco
        clienteManager.salvarCliente(cliente1);

        System.out.println("Cliente salvo com ID: " + cliente1.getId() + "\n");


    } catch (Exception e) {
        System.out.println("ERRO no programa: " + e.getMessage());
        e.printStackTrace(); //metodo para ajudar a encontrar os bugd do sql.
    }

 }

}
