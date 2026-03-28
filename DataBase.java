
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private static final String URL = "jdbc:sqlite:./tarefas.db";

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro de banco: driver SQLite nao encontrado.");
            return null;
        }

        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Erro de banco ao conectar.");
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
        String sql = "CREATE TABLE IF NOT EXISTS tarefas ("
                + "id INTEGER PRIMARY KEY,"
                + "name TEXT NOT NULL UNIQUE,"
                + "date TEXT NOT NULL,"
            + "lvl REAL NOT NULL DEFAULT 0,"
                + "horario TEXT NOT NULL,"
                + "isDone BOOLEAN NOT NULL"
                + ");";

        try (Connection conn = connect()) {
            if (conn == null) {
                System.out.println("Erro: Falha ao conectar ao banco de dados para criar tabela tarefas");
                return;
            }

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);

                boolean temHorario = false;
                boolean temIsDone = false;
                boolean temLvl = false;
                try (ResultSet rs = stmt.executeQuery("PRAGMA table_info(tarefas)")) {
                    while (rs.next()) {
                        String coluna = rs.getString("name");
                        if ("horario".equalsIgnoreCase(coluna)) {
                            temHorario = true;
                        }
                        if ("isDone".equalsIgnoreCase(coluna)) {
                            temIsDone = true;
                        }
                        if ("lvl".equalsIgnoreCase(coluna)) {
                            temLvl = true;
                        }
                    }
                }

                if (!temLvl) {
                    stmt.execute("ALTER TABLE tarefas ADD COLUMN lvl REAL NOT NULL DEFAULT 0");
                }

                if (!temHorario) {
                    stmt.execute("ALTER TABLE tarefas ADD COLUMN horario TEXT NOT NULL DEFAULT ''");
                }
                if (!temIsDone) {
                    stmt.execute("ALTER TABLE tarefas ADD COLUMN isDone BOOLEAN NOT NULL DEFAULT 0");
                }
            }
            System.out.println("Tabela tarefas criada com sucesso!");
        } catch (NullPointerException e) {
            System.out.println("Erro ao criar tabela tarefas.");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela tarefas.");
        }
    }

    public static void criarTabelaClientes() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "email TEXT NOT NULL UNIQUE,"
                + "telefone TEXT NOT NULL UNIQUE"
                + ");";

        Connection conn = null;
        try {
            conn = connect();
            if (conn == null) {
                System.out.println("Erro: Falha ao conectar ao banco de dados para criar tabela clientes");
                return;
            }
            
            Statement stmt = conn.createStatement();
            if (stmt == null) {
                System.out.println("Erro: Falha ao criar statement para tabela clientes");
                return;
            }
            
            stmt.execute(sql);
            stmt.close();
            System.out.println("Tabela clientes criada com sucesso!");
        } catch (NullPointerException e) {
            System.out.println("Erro ao criar tabela clientes.");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela clientes.");
        }
    }

    public static void criarTabelaClienteTarefas() {
        String sql = "CREATE TABLE IF NOT EXISTS cliente_tarefas ("
                + "cliente_id INTEGER NOT NULL,"
                + "tarefa_id INTEGER NOT NULL,"
                + "PRIMARY KEY (cliente_id, tarefa_id),"
                + "FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (tarefa_id) REFERENCES tarefas(id) ON DELETE CASCADE"
                + ");";

        Connection conn = null;
        try {
            conn = connect();
            if (conn == null) {
                System.out.println("Erro: Falha ao conectar ao banco de dados para criar tabela cliente_tarefas");
                return;
            }
            
            Statement stmt = conn.createStatement();
            if (stmt == null) {
                System.out.println("Erro: Falha ao criar statement para tabela cliente_tarefas");
                return;
            }
            
            stmt.execute(sql);
            stmt.close();
            System.out.println("Tabela cliente_tarefas criada com sucesso!");
        } catch (NullPointerException e) {
            System.out.println("Erro ao criar tabela cliente_tarefas.");
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela cliente_tarefas.");
        }
    }
}


