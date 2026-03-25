import java.util.Scanner;
public class Main {
 public static void main(String[] args) {
  Scanner t = new Scanner(System.in);
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
 System.out.println("==============================================\n");
       
 while (true) {
            System.out.println("Digite o número da opção desejada:");
            System.out.println("1 - Criar cliente");
            System.out.println("2 - Criar tarefa");
            System.out.println("3 - Vincular tarefa a cliente");
            System.out.println("4 - Listar tarefas");
            System.out.println("5 - Listar tarefas de um cliente");
            System.out.println("6 - Marcar tarefa como feita");
            System.out.println("7 - Alterar data de uma tarefa");
            System.out.println("0 - Sair");

            int option = t.nextInt();
            t.nextLine(); //buffer

            switch (option) {
                case 1:
                    cm.createCliente();
                    break;
                case 2:
                    Cliente selectedCliente = cm.selectCliente();
                    if (selectedCliente != null) {
                        Task.personalTask(selectedCliente);
                    } else {
                        System.out.println("Cliente não selecionado.");
                    }
                    break;
                case 3:
                    Cliente cliente = cm.selectCliente();
                    Task tarefa = tm.selectTask();
                    if (cliente != null && tarefa != null) {
                        cm.vincularTarefaAoCliente(cliente.getId(), tarefa);
                    } else {
                        System.out.println("Cliente ou tarefa não selecionada.");
                    }
                    break;
                case 4:
                    tm.listTasks();
                    break;
                case 5: 
                    Cliente clienteForTasks = cm.selectCliente();
                    if (clienteForTasks != null) {
                        cm.listarTarefasDoCliente(clienteForTasks.getId());
                    } else {
                        System.out.println("Cliente não selecionado.");
                    }
                    break;
                case 6:
                    Task taskToMark = tm.selectTask();
                    if (taskToMark != null) {
                        tm.updateDoneTask(taskToMark, true);
                    } else {
                        System.out.println("Tarefa não selecionada.");
                    }
                    break;
                    //ARRUMAR : CLIENTES NAO ESTAO LISTADOS COMO VINCULAODS A UMA TAREFA.
                case 7:
                    Task taskToUpdate = tm.selectTask();
                    if (taskToUpdate != null) {
                        System.out.println("Digite a nova data para a tarefa (formato: DD-MM-YYYY):");
                        String newDate = t.nextLine();
                        tm.updateDateTask(taskToUpdate, newDate);
                    } else {
                        System.out.println("Tarefa não selecionada.");
                    }
                    break;
                case 0:
                    System.out.println("Encerrando programa...");
                    t.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        
 

    } catch (Exception e) {
        System.out.println("ERRO no programa: " + e.getMessage());
        e.printStackTrace(); //metodo para ajudar a encontrar os bugd do sql, usado no trycath.
    }

 }

}
