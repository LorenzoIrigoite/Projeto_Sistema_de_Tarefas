import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClienteManager {

    private final ArrayList<Cliente> clientes = new ArrayList<>();

    // salva cliente no banco de dados
    public void salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome, email, telefone) VALUES(?, ?, ?)";

        try (Connection conn = DataBase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefone());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                        System.out.println("Cliente salvo no banco com ID: " + cliente.getId());
                    }
                }
            }

        } catch (SQLException e) { //mensagem de erro do sql qnd ja tem algo igual
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                if (e.getMessage().contains("email")) {
                    System.out.println(" ERRO: Já existe um cliente com este email!");
                } else if (e.getMessage().contains("telefone")) {
                    System.out.println(" ERRO: Já existe um cliente com este telefone!");
                } else {
                    System.out.println(" ERRO: Dados duplicados no cadastro do cliente!");
                }
            } else {
                System.out.println("Erro ao salvar cliente no banco: " + e.getMessage());
            }
        }
    }

    // adiciona a tarefa ao cliente no bd.
    public void vincularTarefaAoCliente(int clienteId, Task tarefa) {
        String sql = "INSERT OR IGNORE INTO cliente_tarefas(cliente_id, tarefa_id) VALUES(?, ?)";

        try (Connection conn = DataBase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, tarefa.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Tarefa " + tarefa.getId() + " vinculada ao cliente " + clienteId);
            } else {
                System.out.println("Tarefa já estava vinculada ao cliente");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao vincular tarefa ao cliente: " + e.getMessage());
        }
    }

    // remove tarefa do cliente no bd.
    public void desvincularTarefaDoCliente(int clienteId, int tarefaId) {
        String sql = "DELETE FROM cliente_tarefas WHERE cliente_id = ? AND tarefa_id = ?";

        try (Connection conn = DataBase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, tarefaId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Tarefa " + tarefaId + " desvinculada do cliente " + clienteId);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao desvincular tarefa do cliente: " + e.getMessage());
        }
    }

    public void carregarClientes() {
        clientes.clear();

        String sql = "SELECT c.id, c.nome, c.email, c.telefone, " +
                    "t.id as tarefa_id, t.name, t.date, t.horario, t.isDone " +
                    "FROM clientes c " +
                    "LEFT JOIN cliente_tarefas ct ON c.id = ct.cliente_id " +
                    "LEFT JOIN tarefas t ON ct.tarefa_id = t.id " +
                    "ORDER BY c.id";

        try (Connection conn = DataBase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            Map<Integer, Cliente> clienteMap = new HashMap<>(); //hash map para rodar em 0(1) 

            while (rs.next()) {
                int clienteId = rs.getInt("id");

                Cliente cliente = clienteMap.get(clienteId);
                if (cliente == null) {
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    String telefone = rs.getString("telefone");
                    cliente = new Cliente(clienteId, nome, email, telefone);
                    clienteMap.put(clienteId, cliente);
                }

                int tarefaId = rs.getInt("tarefa_id");
                if (!rs.wasNull() && rs.getString("name") != null) {
                    String taskName = rs.getString("name");
                    String taskDate = rs.getString("date");
                    String taskHorario = rs.getString("horario");
                    boolean taskDone = rs.getBoolean("isDone");

                    Task tarefa = new Task(tarefaId, taskName, taskDate, taskHorario);
                    tarefa.setIsDone(taskDone);
                    cliente.adicionarTarefa(tarefa);
                }
            }

            clientes.addAll(clienteMap.values());

        } catch (SQLException e) {
            System.out.println("Erro ao carregar clientes: " + e.getMessage());
        }
    }

    public void listarClientes() {
        carregarClientes(); 

        System.out.println("\n=== LISTA DE CLIENTES ===");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
            System.out.println("---");
        }
    }


    public void listarClientesComTarefasPendentes() {
        carregarClientes(); 

        System.out.println("\n=== CLIENTES COM TAREFAS PENDENTES ===");
        for (Cliente cliente : clientes) {
            boolean temPendentes = false;
            for (Task tarefa : cliente.getTarefas()) {
                if (!tarefa.getIsDone()) {
                    if (!temPendentes) {
                        System.out.println("Cliente: " + cliente.getNome());
                        temPendentes = true;
                    }
                    System.out.println("  - " + tarefa.toString());
                }
            }
            if (temPendentes) {
                System.out.println("---");
            }
        }
    }

    public void marcarTarefaClienteComoConcluida(int clienteId, int tarefaId) {
   
        TaskManeger tm = new TaskManeger();
        tm.updateDoneTask(new Task(tarefaId, "", "", ""), true);

  
        carregarClientes();
        System.out.println("Tarefa " + tarefaId + " do cliente " + clienteId + " marcada como concluída!");
    }


    public Cliente getClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }


    public void createCliente() {
        Scanner t = new Scanner(System.in);
        System.out.println("Digite o nome do cliente:");
        String nome = t.nextLine();
        System.out.println("Digite o email do cliente:");
        String email = t.nextLine();
        System.out.println("Digite o telefone do cliente:");
        String telefone = t.nextLine();
        
        Cliente cliente = new Cliente(nome, email, telefone);
        salvarCliente(cliente);
        clientes.add(cliente);
    
    }

    public Cliente selectCliente() {
        carregarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return null;
        }
        System.out.println("Selecione um cliente:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNome());
        }
        Scanner t = new Scanner(System.in);
        int choice = t.nextInt();
        if (choice > 0 && choice <= clientes.size()) {
            return clientes.get(choice - 1);
        } else {
            System.out.println("Seleção inválida.");
            return null;
        }
    }

    public void listarTarefasDoCliente(int clienteId) {
        Cliente cliente = getClientePorId(clienteId);
        if (cliente != null) {
            System.out.println("\n=== TAREFAS DO CLIENTE: " + cliente.getNome() + " ===");
            if (cliente.temTarefas()) {
                for (Task tarefa : cliente.getTarefas()) {
                    System.out.println(tarefa);
                }
            } else {
                System.out.println("Cliente não possui tarefas vinculadas.");
            }
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    public void editarCliente(Cliente cliente) {
        Scanner t = new Scanner(System.in);
        
        System.out.println("\n=== EDITAR CLIENTE: " + cliente.getNome() + " ===");
        System.out.println("Digite o novo nome (ou pressione Enter para manter: " + cliente.getNome() + "):");
        String novoNome = t.nextLine();
        if (novoNome.isEmpty()) {
            novoNome = cliente.getNome();
        }
        
        System.out.println("Digite o novo email (ou pressione Enter para manter: " + cliente.getEmail() + "):");
        String novoEmail = t.nextLine();
        if (novoEmail.isEmpty()) {
            novoEmail = cliente.getEmail();
        }
        
        System.out.println("Digite o novo telefone (ou pressione Enter para manter: " + cliente.getTelefone() + "):");
        String novoTelefone = t.nextLine();
        if (novoTelefone.isEmpty()) {
            novoTelefone = cliente.getTelefone();
        }
        
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";
        
        try (Connection conn = DataBase.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoEmail);
            pstmt.setString(3, novoTelefone);
            pstmt.setInt(4, cliente.getId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Cliente atualizado com sucesso!");
                
                cliente.setNome(novoNome);
                cliente.setEmail(novoEmail);
                cliente.setTelefone(novoTelefone);
            } else {
                System.out.println("Nenhuma linha foi atualizada.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
    }

   
    public void deletarCliente(int clienteId) {
        Scanner t = new Scanner(System.in);
        
        System.out.println("\n  AVISO: Isso vai deletar o cliente e todas as suas vinculações com tarefas!");
        System.out.println("Tem certeza? (S/N):");
        String confirmacao = t.nextLine();
        
        if (!confirmacao.equalsIgnoreCase("S")) {
            System.out.println("Operação cancelada.");
            return;
        }
        
        String sqlDeleteVinculos = "DELETE FROM cliente_tarefas WHERE cliente_id = ?";
        String sqlDeleteCliente = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = DataBase.connect()) {
            
           
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteVinculos)) {
                pstmt.setInt(1, clienteId);
                int vinculosDeletados = pstmt.executeUpdate();
                System.out.println("Vinculações de tarefas removidas: " + vinculosDeletados);
            }
           
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteCliente)) {
                pstmt.setInt(1, clienteId);
                int affectedRows = pstmt.executeUpdate();
                
                if (affectedRows > 0) {
                    System.out.println("Cliente deletado com sucesso!");
                 
                    for (int i = clientes.size() - 1; i >= 0; i--) {
                        if (clientes.get(i).getId() == clienteId) {
                            clientes.remove(i);
                            break; 
                        }
                    }
                } else {
                    System.out.println("Cliente não encontrado.");
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }
}