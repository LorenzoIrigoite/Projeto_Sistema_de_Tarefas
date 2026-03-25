import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TaskManeger {

    private ArrayList<Task> tarefas = new ArrayList<>();


    public void addTask(Task t) {
   
    String sql = "INSERT INTO tarefas(id, name, date, lvl, isDone) VALUES(?, ?, ?, ?, ?)";

    try (Connection conn = DataBase.connect(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // Vincula os dados do seu objeto Task aos '?'
        pstmt.setInt(1, t.getId());
        pstmt.setString(2, t.getName());
        pstmt.setString(3, t.getDate());
        pstmt.setDouble(4, t.getLvl());
        pstmt.setBoolean(5, t.getIsDone());

        // Executa a gravação no arquivo .db
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
        String sql = "SELECT id, name, date, lvl, isDone FROM tarefas";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                double lvl = rs.getDouble("lvl");
                boolean isDone = rs.getBoolean("isDone");
                System.out.println("Task ID: " + id + " | Task: " + name + " | Date: " + date + " | Level: " + lvl + " | Done: " + isDone);
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
        System.out.println("Data alterada para: "+date);

    } catch (SQLException e) {
        System.out.println("Erro ao salvar no banco: " + e.getMessage());
    }
}


        
}



