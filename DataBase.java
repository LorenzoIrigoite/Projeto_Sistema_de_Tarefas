import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


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

//tabela tarefas.
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS tarefas (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "horario TEXT NOT NULL," +
                "isDone BOOLEAN NOT NULL" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela tarefas criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela tarefas: " + e.getMessage());
        }
    }
//tabela clientes.
    public static void criarTabelaClientes() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "telefone TEXT NOT NULL" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela clientes criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela clientes: " + e.getMessage());
        }
    }
// tabela de relacionamento entre clientes e tarefas.
    public static void criarTabelaClienteTarefas() {
        String sql = "CREATE TABLE IF NOT EXISTS cliente_tarefas (" +
                "cliente_id INTEGER NOT NULL," +
                "tarefa_id INTEGER NOT NULL," +
                "PRIMARY KEY (cliente_id, tarefa_id)," +
                "FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE," +
                "FOREIGN KEY (tarefa_id) REFERENCES tarefas(id) ON DELETE CASCADE" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela cliente_tarefas criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela cliente_tarefas: " + e.getMessage());
        }
    }

    // Método para recriar a tabela tarefas com a nova estrutura
    public static void recriarTabelaTarefas() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // Dropar tabela existente se existir
            stmt.execute("DROP TABLE IF EXISTS tarefas");

            // Recriar tabela com nova estrutura
            String sql = "CREATE TABLE tarefas (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "date TEXT NOT NULL," +
                    "horario TEXT NOT NULL," +
                    "isDone BOOLEAN NOT NULL" +
                    ");";

            stmt.execute(sql);
            System.out.println("Tabela tarefas recriada com sucesso com coluna 'horario'!");

        } catch (Exception e) {
            System.out.println("Erro ao recriar tabela tarefas: " + e.getMessage());
        }
    }

//metodo para resetar o auto-increment da tabela tarefas.
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
