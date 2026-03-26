public class Main {
 public static void main(String[] args) {
  
    try {
        DataBase.recriarTabelaTarefas();
        DataBase.criarTabelaClientes();
        DataBase.criarTabelaClienteTarefas();
        App.executar();
         } catch (Exception e) {
        System.out.println("ERRO no programa: " + e.getMessage());
        e.printStackTrace();
             }
 }

}
