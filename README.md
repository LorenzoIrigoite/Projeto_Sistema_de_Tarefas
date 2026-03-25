# ProjetoL - Task Manager com Clientes

Sistema de gerenciamento de tarefas e clientes em Java + SQLite.

## Funcionalidades

### 🗂️ Gerenciamento de Tarefas
- Criar tarefas com ID manual
- Listar todas as tarefas
- Remover tarefas por ID
- Atualizar status (concluída/pendente)
- Atualizar data da tarefa

### 👥 Gerenciamento de Clientes
- Criar clientes com informações básicas (nome, email, telefone)
- **Vincular múltiplas tarefas aos clientes** ⭐ **NOVO**
- Verificar se cliente tem tarefas
- Marcar tarefa específica do cliente como concluída
- Desvincular tarefa do cliente
- Listar clientes com tarefas pendentes
- Salvar e carregar clientes do banco de dados
- Listar tarefas de um cliente específico

## Estrutura das Classes

### Task
- `id`: Identificador único (definido manualmente)
- `name`: Nome da tarefa
- `date`: Data da tarefa
- `horario`: Horário específico da tarefa (ex: "14:30", "09:00")
- `isDone`: Status da tarefa

### Cliente
- `id`: Identificador único do banco de dados
- `nome`: Nome do cliente
- `email`: Email do cliente
- `telefone`: Telefone do cliente
- `tarefas`: Lista de objetos Task vinculados (ArrayList<Task>)

### TaskManager
Gerencia operações no banco de dados para tarefas:
- `addTask(Task t)`: Salva tarefa no banco
- `removeTask(int id)`: Remove tarefa por ID
- `listTasks()`: Lista todas as tarefas
- `updateDoneTask(Task t, boolean isDone)`: Atualiza status
- `updateDateTask(Task t, String date)`: Atualiza data

### ClienteManager
Gerencia operações para clientes no banco de dados:
- `salvarCliente(Cliente cliente)`: Salva cliente no banco
- `carregarClientes()`: Carrega clientes e suas tarefas do banco
- `listarClientes()`: Lista todos os clientes com suas tarefas
- `vincularTarefaAoCliente(int clienteId, Task tarefa)`: Vincula tarefa a cliente
- `desvincularTarefaDoCliente(int clienteId, int tarefaId)`: Remove vínculo
- `listarTarefasDoCliente(int clienteId)`: Lista tarefas de um cliente
- `marcarTarefaClienteComoConcluida(int clienteId, int tarefaId)`: Marca tarefa como concluída

## Estrutura do Banco de Dados

### Tabelas
- **tarefas**: Armazena as tarefas
- **clientes**: Armazena os clientes
- **cliente_tarefas**: Tabela de relacionamento N:N entre clientes e tarefas

### Relacionamentos
- Um cliente pode ter múltiplas tarefas
- Uma tarefa pode estar vinculada a múltiplos clientes
- Relacionamento gerenciado pela tabela `cliente_tarefas`

## Como Usar

1. **Compilar o projeto:**
   ```bash
   javac -cp ".;sqlite-jdbc.jar" *.java
   ```

2. **Executar o programa:**
   ```bash
   java -cp ".;sqlite-jdbc.jar" Main
   ```

3. **Funcionalidades demonstradas:**
   - Criação de tarefas e clientes
   - Vinculação de múltiplas tarefas aos clientes
   - Listagem de clientes e suas tarefas
   - Marcação de tarefas como concluídas
   - Desvinculação de tarefas

## Dependências

- Java 8+
- SQLite JDBC Driver (sqlite-jdbc.jar)
- Sistema operacional: Windows/Linux/Mac

## Arquivos do Projeto

- `Main.java`: Classe principal com demonstração do sistema
- `Task.java`: Classe que representa uma tarefa
- `Cliente.java`: Classe que representa um cliente
- `TaskManager.java`: Gerenciador de operações de tarefas
- `ClienteManager.java`: Gerenciador de operações de clientes
- `DataBase.java`: Utilitários para conexão e criação de tabelas
- `README.md`: Este arquivo de documentação
- `vincularTarefaAoCliente(int index, Task tarefa)`: Vincula tarefa
- `marcarTarefaClienteComoConcluida(int index)`: Marca tarefa como concluída
- `listarClientesComTarefasPendentes()`: Lista clientes com tarefas pendentes

## Como Usar

### Criando um Cliente
```java
Cliente cliente = new Cliente("João Silva", "joao@email.com", "(11) 99999-0001");
```

### Criando um Cliente com Tarefa
```java
Task tarefa = new Task(1, "Revisar código", "25/03/2026", "14:30");
Cliente cliente = new Cliente("Maria Santos", "maria@email.com", "(11) 99999-0002", tarefa);
```

### Vinculando Tarefa a Cliente Existente
```java
cliente.setTarefa(tarefa);
```

### Verificando se Cliente tem Tarefa
```java
if (cliente.temTarefa()) {
    System.out.println("Cliente tem tarefa: " + cliente.getTarefa().getName());
}
```

### Marcando Tarefa como Concluída
```java
cliente.marcarTarefaComoConcluida();
```

## Banco de Dados

- **Arquivo**: `tarefas.db`
- **Tabela**: `tarefas`
- **Driver**: SQLite JDBC

### Estrutura da Tabela
```sql
CREATE TABLE tarefas (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    date TEXT NOT NULL,
    horario TEXT NOT NULL,
    isDone BOOLEAN NOT NULL
);

CREATE TABLE clientes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT NOT NULL,
    telefone TEXT NOT NULL,
    tarefa_id INTEGER,
    FOREIGN KEY (tarefa_id) REFERENCES tarefas(id)
);
```

## Dependências

- Java 8+
- SQLite JDBC Driver (incluído no classpath do VS Code)

## Executando

1. Abra o projeto no VS Code
2. Execute a classe `Main.java`
3. O sistema criará a tabela automaticamente se não existir
