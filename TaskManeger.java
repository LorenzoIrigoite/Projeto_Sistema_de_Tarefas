import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManeger {

    public void addTask(Task t) {
   
    String sql = "INSERT INTO tarefas(id, name, date, horario, isDone) VALUES(?, ?, ?, ?, ?)";

    try (Connection conn = DataBase.connect(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, t.getId());
        pstmt.setString(2, t.getName());
        pstmt.setString(3, t.getDate());
        pstmt.setString(4, t.getHorario());
        pstmt.setBoolean(5, t.getIsDone());

        pstmt.executeUpdate();
        System.out.println("Tarefa salva com sucesso!");

    } catch (SQLException e) {
        System.out.println("Erro ao salvar no banco: " + e.getMessage());
    }
}
    public void removeTask(int id){
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (Connection conn = DataBase.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Tarefa removida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover do banco: " + e.getMessage());
        }
    }

    public void listTasks(){
        Connection conn = DataBase.connect();
        if (conn == null) {
            System.out.println("Erro ao conectar ao banco de dados");
            return;
        }
        String sql = "SELECT id, name, date, horario, isDone FROM tarefas";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String horario = rs.getString("horario");
                boolean isDone = rs.getBoolean("isDone");
                System.out.println("Task ID: " + id + " | Task: " + name + " | Date: " + date + " | Horario: " + horario + " | Done: " + isDone);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    public void updateDoneTask(Task t, boolean isDone) {
   
    String sql = "UPDATE tarefas SET isDone = ? WHERE id = ?";

    try (Connection conn = DataBase.connect(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setBoolean(1, isDone);
        pstmt.setInt(2, t.getId());

        pstmt.executeUpdate();
        System.out.println("Tarefa feita, muito bem!");

    } catch (SQLException e) {
        System.out.println("Erro ao salvar no banco: " + e.getMessage());
    }
}

    public void updateDateTask(Task t, String date) {
   
    String sql = "UPDATE tarefas SET date = ? WHERE id = ?";

    try (Connection conn = DataBase.connect(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, date);
        pstmt.setInt(2, t.getId());

        pstmt.executeUpdate();
        System.out.println("Data da tarefa atualizada!");

    } catch (SQLException e) {
        System.out.println("Erro ao atualizar data: " + e.getMessage());
    }
}

    // Selecionar tarefa via lista
    public Task selectTask() {
        Connection conn = DataBase.connect();
        if (conn == null) {
            System.out.println("Erro ao conectar ao banco de dados");
            return null;
        }
        String sql = "SELECT id, name, date, horario, isDone FROM tarefas";
        ArrayList<Task> tasks = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String horario = rs.getString("horario");
                boolean isDone = rs.getBoolean("isDone");
                Task task = new Task(id, name, date, horario);
                task.setIsDone(isDone);
                tasks.add(task);
                System.out.println((tasks.size()) + " - " + task.toString());
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return null;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione uma tarefa pelo número:");
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= tasks.size()) {
            return tasks.get(choice - 1);
        } else {
            System.out.println("Seleção inválida.");
            return null;
        }
    }


        
}



