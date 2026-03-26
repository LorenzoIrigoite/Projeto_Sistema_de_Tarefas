import java.util.Scanner;

public class App {
  
    public static void executar(){
        Scanner t = new Scanner(System.in);
        TaskManeger tm = new TaskManeger();
        ClienteManager cm = new ClienteManager();
        System.out.println("Iniciando programa...");

        try {
            System.out.println("==============================================\n");
           
         while (true) {
            System.out.println("==============================\n");
                System.out.println("Digite o número da opção desejada:");
                System.out.println("1 - Criar cliente");
                System.out.println("2 - Criar tarefa");
                System.out.println("3 - Vincular tarefa a cliente");
                System.out.println("4 - Listar Clientes");
                System.out.println("5 - Listar tarefas");
                System.out.println("6 - Listar tarefas de um cliente");
                System.out.println("7 - Marcar tarefa como feita");
                System.out.println("8 - Alterar data de uma tarefa");
                System.out.println("9 - Editar cliente");
                System.out.println("10 - Remover cliente");
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
                        cm.listarClientes();
                        break;
                    case 5:
                        tm.listTasks();
                        break;
                    case 6: 
                    Cliente clienteForTasks = cm.selectCliente();
                    if (clienteForTasks != null) {
                        cm.listarTarefasDoCliente(clienteForTasks.getId());
                    } else {
                        System.out.println("Cliente não selecionado.");
                    }
                    break;
                case 7:
                    Task taskToMark = tm.selectTask();
                    if (taskToMark != null) {
                        tm.updateDoneTask(taskToMark, true);
                    } else {
                        System.out.println("Tarefa não selecionada.");
                    }
                    break;
                    //ARRUMAR : CLIENTES NAO ESTAO LISTADOS COMO VINCULAODS A UMA TAREFA.
                case 8:
                    Task taskToUpdate = tm.selectTask();
                    if (taskToUpdate != null) {
                        System.out.println("Digite a nova data para a tarefa (formato: DD-MM-YYYY):");
                        String newDate = t.nextLine();
                        tm.updateDateTask(taskToUpdate, newDate);
                    } else {
                        System.out.println("Tarefa não selecionada.");
                    }
                    break;
                case 9:
                    Cliente clienteToEdit = cm.selectCliente();
                    if (clienteToEdit != null) {
                        cm.editarCliente(clienteToEdit);
                    } else {
                        System.out.println("Cliente não selecionado.");
                    }
                    break;
                case 10:
                    Cliente clienteToDelete = cm.selectCliente();
                    if (clienteToDelete != null) {
                        cm.deletarCliente(clienteToDelete.getId());
                    } else {
                        System.out.println("Cliente não selecionado.");
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