# Sistema de Gerenciamento de Tarefas e Clientes (Java + SQLite)

Este projeto implementa um sistema simplificado para cadastro de clientes e tarefas (CRUD) com persistência em SQLite (usando relacionamento entre mesas N:N) e versionamento de código via Git/GitHub. O objetivo é servir como material de estudo para revisão de conceitos e aprendizado de novas formas de resolver problemas, aplicando validações, tratamento de exceções específicas e o uso de PreparedStatement para evitar SQL Injection, garantindo o uso de parâmetros seguros.

Atualmente, a URL de conexão com o banco de dados está declarada diretamente no código fonte (hardcoded).
Tenho noção que, em um projeto real ou ambiente de produção, esta é uma prática insegura. O correto seria utilizar variáveis de ambiente ou arquivos de configuração (como .env ou application.properties) devidamente ignorados pelo .gitignore. Isso evita a exposição de credenciais e caminhos sensíveis em repositórios públicos.

*Este método está sendo utilizado apenas para facilitar a execução imediata do projeto para fins de estudo.*
---

#  Funcionalidades :

- Cadastro de clientes (nome, email, telefone)
- Cadastro de tarefas (nome, data, horário, status)
- Associação de tarefas a clientes (N:N)
- Atualização de status e datas de tarefas
- Listagem de clientes, tarefas e tarefas específicas por cliente
- Remoção de clientes/tarefas e remoção de vínculo
- Proteção de integridade com `UNIQUE` para email/telefone (clientes) e nome (tarefas)
- Mensagens claras de erros para duplicidade de dados



# Tabelas

- `tarefas`
- `id` INTEGER PRIMARY KEY
- `name` TEXT NOT NULL UNIQUE
- `date` TEXT NOT NULL
- `horario` TEXT NOT NULL
- `isDone` BOOLEAN NOT NULL

-`clientes`
- `id` INTEGER PRIMARY KEY AUTOINCREMENT
- `nome` TEXT NOT NULL
- `email` TEXT NOT NULL UNIQUE
- `telefone` TEXT NOT NULL UNIQUE

- `cliente_tarefas`
- `cliente_id` INTEGER NOT NULL
- `tarefa_id` INTEGER NOT NULL
- PRIMARY KEY (`cliente_id`, `tarefa_id`)
- FOREIGN KEY (`cliente_id`) REFERENCES clientes(id) ON DELETE CASCADE
- FOREIGN KEY (`tarefa_id`) REFERENCES tarefas(id) ON DELETE CASCADE


#  Arquivos

- `Main.java` – Ponto de entrada do programa (executa inicialização e loop de menu)
- `App.java` – Menu de operações e roteamento de comandos
- `Cliente.java` – Modelo de cliente
- `Task.java` – Modelo de tarefa
- `ClienteManager.java` – CRUD de clientes + relacionamento com tarefas
- `TaskManeger.java` – CRUD de tarefas
- `DataBase.java` – Conexão, criação e manutenção de tabelas SQLite

##  Funcionalidades (console)

- Criar cliente
- Criar tarefa
- Asociar tarefa a cliente
- Listar clientes
- Listar tarefas
- Listar tarefas de um cliente
- Marcar tarefa como concluída
- Atualizar data de tarefa
- Editar cliente
- Remover cliente (+ vínculos também)

##  Comportamento de erro / validação

- `UNIQUE constraint failed: clientes.email` → impede cadastro de email duplicado
- `UNIQUE constraint failed: clientes.telefone` → impede cadastro de telefone duplicado
- `UNIQUE constraint failed: tarefas.name` → impede cadastro de tarefa com nome duplicado
- Mensagens de erro traduzidas para o usuário em vez de só mostrar exceção crua


## Proximos passos :

- Interface (GUI / web)
- Persistência via transações (rollback em falhas)
- Pesquisa por atributo do Cliente ou atributo da Tarefa.
- Tratar metodos que ainda nao estao rodando com a eficiencia desejada.

