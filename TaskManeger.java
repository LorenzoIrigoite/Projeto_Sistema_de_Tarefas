import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TaskManeger {

    private ArrayList<Task> tarefas = new ArrayList<>();


    public void addTask(Task t) {
    // 1. O comando SQL com marcadores '?' para segurança
    String sql = "INSERT INTO tarefas(name, date, lvl, isDone) VALUES(?, ?, ?, ?)";

    try (Connection conn = DataBase.connect(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // 2. Vincula os dados do seu objeto Task aos '?'
        pstmt.setString(1, t.getName());
        pstmt.setString(2, t.getDate());
        pstmt.setDouble(3, t.getLvl());
        pstmt.setBoolean(4, t.getIsDone());

        // 3. Executa a gravação no arquivo .db
        pstmt.executeUpdate();
        System.out.println("Tarefa salva com sucesso no banco!");

    } catch (SQLException e) {
        System.out.println("Erro ao salvar no banco: " + e.getMessage());
    }
}



   
    public void removeTask(Task t){
        if(t != null){
            tarefas.remove(t);
        }
        else{
            System.out.println("Your task is already empty!");
        }
    }

    public void listTasks(){
        Connection conn = DataBase.connect();
        if (conn == null) {
            System.out.println("Erro ao conectar ao banco de dados");
            return;
        }
        String sql = "SELECT name, date, lvl, isDone FROM tarefas";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String name = rs.getString("name");
                String date = rs.getString("date");
                double lvl = rs.getDouble("lvl");
                boolean isDone = rs.getBoolean("isDone");
                System.out.println("Task: " + name + " | Date: " + date + " | Level: " + lvl + " | Done: " + isDone);
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

    public void listTask(Task x){
        System.out.println(x.toString());
            } 
        
    public void checkTask(Task t){
        if(t.getIsDone() == true){
            System.out.println("This task is already done!");
        }
        else{
            t.setIsDone(true);
            System.out.println("Task checked!");
        }
    }
        
}



