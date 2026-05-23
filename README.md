# Sistema de Controle de Laboratório

Um sistema de gerenciamento de empréstimos de equipamentos de laboratório desenvolvido em **Java** com persistência em banco de dados **MySQL**. Este projeto adota a arquitetura **DAO (Data Access Object)** para garantir um código limpo, modular e de fácil manutenção, separando as regras de negócio do acesso aos dados.

## Funcionalidades Implementadas

O sistema opera via Interface de Linha de Comando (CLI) e possui as seguintes funcionalidades:

- [x] Cadastro de Alunos (com validação de matrícula única).
- [x] Cadastro de Equipamentos.
- [x] Listagem de Equipamentos Disponíveis.
- [x] Realização de Empréstimos (relacionamento N:M).
- [x] Bloqueio automático de empréstimo para equipamentos ocupados.
- [x] Registro de Devolução (atualizando o status do equipamento).
- [x] Consulta de Empréstimos Ativos.
- [x] Consulta de Histórico Completo de movimentações.

## Tecnologias e Arquitetura

* **Linguagem:** Java (Orientação a Objetos)
* **Banco de Dados:** MySQL Relacional
* **Comunicação:** API JDBC nativa
* **Arquitetura:** Camadas (Model, DAO, Database e Main)
* **Gerenciamento de Transações:** Controle manual de `commit` e `rollback` em operações atômicas (ex: realizar empréstimos).

## Como executar o projeto na sua máquina

### Pré-requisitos
* Java JDK 11 ou superior.
* Servidor MySQL rodando localmente (porta 3306).
* IDE de sua preferência (IntelliJ IDEA, Eclipse, VSCode).

### 1. Configurando o Banco de Dados
Abra o seu SGBD (ex: MySQL Workbench) e execute o script fornecido no projeto para criar as tabelas estruturais:
1. Localize o arquivo `script_banco.sql` na raiz do projeto.
2. Execute o script para criar o banco `controle_laboratorio` e as tabelas `aluno`, `equipamento` e `emprestimo`.

### 2. Configurando as Credenciais (Segurança)
Para manter as credenciais seguras e evitar o *hardcoding*, o sistema lê as variáveis de ambiente através de um arquivo de propriedades. Esse arquivo foi omitido do repositório via `.gitignore`.

Para o projeto rodar na sua máquina, crie um arquivo chamado **`database.properties`** na pasta raiz do projeto com a seguinte estrutura:

```properties
db.url=jdbc:mysql://localhost:3306/controle_laboratorio
db.user=SEU_USUARIO_AQUI
db.password=SUA_SENHA_AQUI
