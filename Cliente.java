import java.util.ArrayList;

public class Cliente{
    private int id; 
    private String nome;
    private String email;
    private String telefone;
    private ArrayList<Task> tarefas; 

    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tarefas = new ArrayList<>();
    }

    public Cliente(int id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tarefas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Métodos para gerenciar múltiplas tarefas
    public ArrayList<Task> getTarefas() {
        return tarefas;
    }

    public void adicionarTarefa(Task tarefa) {
        if (tarefa != null && !tarefas.contains(tarefa)) {
            tarefas.add(tarefa);
        }
    }

    public void removerTarefa(Task tarefa) {
        tarefas.remove(tarefa);
    }

    public void removerTarefa(int tarefaId) {
        tarefas.removeIf(t -> t.getId() == tarefaId);
    }

    public boolean temTarefas() {
        return !tarefas.isEmpty();
    }

    public int getQuantidadeTarefas() {
        return tarefas.size();
    }

    public void marcarTarefaComoConcluida(int tarefaId) {
        for (Task tarefa : tarefas) {
            if (tarefa.getId() == tarefaId) {
                tarefa.setIsDone(true);
                break;
            }
        }
    }

    // Getters e setters para ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(nome)
          .append(" | Email: ").append(email)
          .append(" | Telefone: ").append(telefone)
          .append(" | Tarefas: ").append(tarefas.size());

        if (temTarefas()) {
            sb.append("\nTarefas vinculadas:");
            for (Task tarefa : tarefas) {
                sb.append("\n  - ").append(tarefa.toString());
            }
        } else {
            sb.append("\nSem tarefas vinculadas");
        }
        return sb.toString();
    }
}