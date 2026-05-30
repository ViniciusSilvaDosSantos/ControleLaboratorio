package br.edu.ifpa.laboratorio;

import br.edu.ifpa.laboratorio.dao.AlunoDAO;
import br.edu.ifpa.laboratorio.dao.EmprestimoDAO;
import br.edu.ifpa.laboratorio.dao.EquipamentoDAO;
import br.edu.ifpa.laboratorio.model.Aluno;
import br.edu.ifpa.laboratorio.model.Emprestimo;
import br.edu.ifpa.laboratorio.model.Equipamento;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();
        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n====== SISTEMA DE CONTROLE DE LABORATÓRIO ======");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Cadastrar Equipamento");
            System.out.println("3. Listar Equipamentos Disponíveis");
            System.out.println("4. Realizar Empréstimo");
            System.out.println("5. Registrar Devolução");
            System.out.println("6. Listar Empréstimos Ativos"); // <-- Opção nova aqui!
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n-- NOVO ALUNO --");
                    System.out.print("Digite o nome do aluno: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a matrícula: ");
                    String matricula = scanner.nextLine();

                    Aluno aluno = new Aluno(0, nome, matricula);
                    alunoDAO.cadastrar(aluno);
                    break;

                case 2:
                    System.out.println("\n-- NOVO EQUIPAMENTO --");
                    System.out.print("Digite a descrição do equipamento: ");
                    String descricao = scanner.nextLine();

                    Equipamento equipamento = new Equipamento(0, descricao, true);
                    equipamentoDAO.cadastrar(equipamento);
                    break;

                case 3:
                    System.out.println("\n-- EQUIPAMENTOS DISPONÍVEIS --");
                    List<Equipamento> disponiveis = equipamentoDAO.listarDisponiveis();
                    if (disponiveis.isEmpty()) {
                        System.out.println("Nenhum equipamento disponível no momento.");
                    } else {
                        for (Equipamento eq : disponiveis) {
                            System.out.println(" -> " + eq.toString());
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n-- REALIZAR EMPRÉSTIMO --");
                    System.out.print("Digite o ID do Aluno: ");
                    int idAluno = scanner.nextInt();
                    System.out.print("Digite o ID do Equipamento: ");
                    int idEquipamento = scanner.nextInt();

                    Aluno a = new Aluno();
                    a.setId(idAluno);
                    Equipamento e = new Equipamento();
                    e.setId(idEquipamento);

                    Emprestimo emprestimo = new Emprestimo();
                    emprestimo.setAluno(a);
                    emprestimo.setEquipamento(e);

                    emprestimoDAO.realizarEmprestimo(emprestimo);
                    break;

                case 5:
                    System.out.println("\n-- REGISTRAR DEVOLUÇÃO --");
                    System.out.print("Digite o ID do Empréstimo: ");
                    int idEmp = scanner.nextInt();
                    System.out.print("Digite o ID do Equipamento sendo devolvido: ");
                    int idEqDevolvido = scanner.nextInt();

                    emprestimoDAO.registrarDevolucao(idEmp, idEqDevolvido);
                    break;

                case 6: // <-- Lógica da nossa opção nova!
                    System.out.println("\n-- EMPRÉSTIMOS ATIVOS --");
                    emprestimoDAO.listarEmprestimosAtivos();
                    break;

                case 0:
                    System.out.println("\nEncerrando o sistema... Até logo!");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }
}