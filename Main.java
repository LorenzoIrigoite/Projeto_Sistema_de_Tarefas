public class Main {
 public static void main(String[] args) {
  
    try {
        DataBase.criarTabela();
        DataBase.criarTabelaClientes();
        DataBase.criarTabelaClienteTarefas();
        App.executar();
         } catch (Exception e) {
        System.out.println("ERRO no programa: " + e.getMessage());
        e.printStackTrace();
             }
 }

}
