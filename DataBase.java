
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
        String sql = "CREATE TABLE IF NOT EXISTS tarefas ("
                + "id INTEGER PRIMARY KEY,"
                + "name TEXT NOT NULL UNIQUE,"
                + "date TEXT NOT NULL,"
                + "horario TEXT NOT NULL,"
                + "isDone BOOLEAN NOT NULL"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela tarefas criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela tarefas: " + e.getMessage());
        }
    }
//tabela clientes.

    public static void criarTabelaClientes() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "email TEXT NOT NULL UNIQUE,"
                + "telefone TEXT NOT NULL UNIQUE"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela clientes criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela clientes: " + e.getMessage());
        }
    }
// tabela de relacionamento entre clientes e tarefas.

    public static void criarTabelaClienteTarefas() {
        String sql = "CREATE TABLE IF NOT EXISTS cliente_tarefas ("
                + "cliente_id INTEGER NOT NULL,"
                + "tarefa_id INTEGER NOT NULL,"
                + "PRIMARY KEY (cliente_id, tarefa_id),"
                + "FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (tarefa_id) REFERENCES tarefas(id) ON DELETE CASCADE"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela cliente_tarefas criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela cliente_tarefas: " + e.getMessage());
        }
    }
}


