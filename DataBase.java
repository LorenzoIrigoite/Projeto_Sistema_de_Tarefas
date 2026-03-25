import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class DataBase {

    private static final String URL = "jdbc:sqlite:tarefas.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }

    public static String desconnect(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao desconectar: " + e.getMessage());
        }
        return "Desconectado com sucesso!";
    }


    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS tarefas (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "lvl REAL NOT NULL," +
                "isDone BOOLEAN NOT NULL" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public static void resetarAutoIncrement() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='tarefas'");
            System.out.println("Auto-increment resetado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao resetar auto-increment: " + e.getMessage());
        }
    }
}
