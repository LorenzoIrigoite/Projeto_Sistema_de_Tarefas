
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManeger {

    public void addTask(Task t) {
        if (t == null) {
            System.out.println("ERRO: Tarefa não podem NULL!");
            return;
        }

        String sql = "INSERT INTO tarefas(name, date, lvl, horario, isDone) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DataBase.connect()) {
            if (conn == null) {
                System.out.println("Erro: Falha ao conectar ao banco de dados");
                return;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, t.getName());
                pstmt.setString(2, t.getDate());
                pstmt.setDouble(3, 0.0);
                pstmt.setString(4, t.getHorario());
                pstmt.setBoolean(5, t.getIsDone());

                pstmt.executeUpdate();
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        t.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Tarefa salva com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar tarefa.");
        } catch (NullPointerException e) {
            System.out.println("Erro ao adicionar tarefa.");
        }
    }

    public void removeTask(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        Connection conn = null;
        try {
            conn = DataBase.connect();
            if (conn == null) {
                System.out.println("Erro: Falha ao conectar ao banco de dados");
                return;
            }
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (pstmt == null) {
                System.out.println("Erro: Falha ao preparar statement");
                return;
            }

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Tarefa removida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover tarefa.");
        }
    }

    public void listTasks() {
        Connection conn = null;
        try {
            conn = DataBase.connect();
            if (conn == null) {
                System.out.println("ERRO: Falha ao conectar ao banco de dados");
                return;
            }
            
            String sql = "SELECT id, name, date, horario, isDone FROM tarefas";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (pstmt == null) {
                    System.out.println("ERRO: Falha ao preparar statement!");
                    return;
                }
                
                ResultSet rs = pstmt.executeQuery();
                if (rs == null) {
                    System.out.println("ERRO: Falha ao executar query!");
                    return;
                }

                System.out.println("\n=== LISTA DE TAREFAS ===");
                boolean hasRows = false;
                while (rs.next()) {
                    hasRows = true;
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String date = rs.getString("date");
                    String horario = rs.getString("horario");
                    boolean isDone = rs.getBoolean("isDone");
                    System.out.println("Task ID: " + id + " | Task: " + name + " | Date: " + date + " | Horario: " + horario + " | Done: " + isDone);
                }
                
                if (!hasRows) {
                    System.out.println("Nenhuma tarefa cadastrada.");
                }
                System.out.println("=========================\n");
                rs.close();

            } catch (SQLException e) {
                System.out.println("Erro ao listar tarefas.");
            }
        } catch (NullPointerException e) {
            System.out.println("Erro ao listar tarefas.");
        } catch (Exception e) {
            System.out.println("Erro ao listar tarefas.");
        }
    }

    public void updateDoneTask(Task t, boolean isDone) {
        if (t == null) {
            System.out.println("ERRO: Tarefa não pode ser NULL!");
            return;
        }

        String sql = "UPDATE tarefas SET isDone = ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DataBase.connect();
            if (conn == null) {
                System.out.println("Erro: Falha ao conectar ao banco de dados");
                return;
            }
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (pstmt == null) {
                System.out.println("Erro: Falha ao preparar statement");
                return;
            }

            pstmt.setBoolean(1, isDone);
            pstmt.setInt(2, t.getId());

            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Tarefa feita, muito bem!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar no banco.");

        }
    }

    public void updateDateTask(Task t, String date) {
        if (t == null || date == null || date.trim().isEmpty()) {
            System.out.println("ERRO: Tarefa ou data não pode ser NULL!");
            return;
        }

        String sql = "UPDATE tarefas SET date = ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DataBase.connect();
            if (conn == null) {
                System.out.println("Erro: Falha ao conectar ao banco de dados");
                return;
            }
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (pstmt == null) {
                System.out.println("Erro: Falha ao preparar statement");
                return;
            }

            pstmt.setString(1, date);
            pstmt.setInt(2, t.getId());

            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Data da tarefa atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar data.");
        }
    }

    public Task selectTask() {
        return selectTask(new Scanner(System.in));
    }

    public Task selectTask(Scanner scanner) {
        Connection conn = null;
        try {
            conn = DataBase.connect();
            if (conn == null) {
                System.out.println("ERRO: Falha ao conectar ao banco de dados");
                return null;
            }
            
            String sql = "SELECT id, name, date, horario, isDone FROM tarefas";
            ArrayList<Task> tasks = new ArrayList<>();
            
            if (tasks == null) {
                System.out.println("ERRO: Falha ao criar lista de tarefas!");
                return null;
            }
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (pstmt == null) {
                    System.out.println("ERRO: Falha ao preparar statement!");
                    return null;
                }
                
                ResultSet rs = pstmt.executeQuery();
                if (rs == null) {
                    System.out.println("ERRO: Falha ao executar query!");
                    return null;
                }

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String date = rs.getString("date");
                    String horario = rs.getString("horario");
                    boolean isDone = rs.getBoolean("isDone");
                    Task task = new Task(id, name, date, horario);
                    if (task != null) {
                        task.setIsDone(isDone);
                        tasks.add(task);
                        System.out.println((tasks.size()) + " - " + task.toString());
                    }
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("Erro ao listar tarefas.");
            }

            if (tasks.isEmpty()) {
                System.out.println("Nenhuma tarefa encontrada.");
                return null;
            }

            System.out.println("Selecione uma tarefa pelo número:");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice > 0 && choice <= tasks.size()) {
                    Task selecionada = tasks.get(choice - 1);
                    if (selecionada == null) {
                        System.out.println("ERRO: Tarefa selecionada é NULL!");
                        return null;
                    }
                    return selecionada;
                } else {
                    System.out.println("Seleção inválida.");
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Erro ao ler seleção.");
                return null;
            }
            
        } catch (NullPointerException e) {
            System.out.println("Erro ao selecionar tarefa.");
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao selecionar tarefa.");
            return null;
        }
    }

}
