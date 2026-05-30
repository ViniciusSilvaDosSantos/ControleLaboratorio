package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.ConexaoMySQL;
import br.edu.ifpa.laboratorio.model.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmprestimoDAO {

    public void realizarEmprestimo(Emprestimo emprestimo) {
        String sqlVerificar = "SELECT disponivel FROM equipamento WHERE id = ?";
        String sqlEmprestimo = "INSERT INTO emprestimo (id_aluno, id_equipamento, status) VALUES (?, ?, 'ATIVO')";
        String sqlAtualizaEquip = "UPDATE equipamento SET disponivel = false WHERE id = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {

            try (PreparedStatement stmtVerifica = con.prepareStatement(sqlVerificar)) {
                stmtVerifica.setInt(1, emprestimo.getEquipamento().getId());
                try (ResultSet rs = stmtVerifica.executeQuery()) {
                    if (rs.next()) {
                        if (!rs.getBoolean("disponivel")) {
                            System.out.println("BLOQUEADO: O equipamento selecionado NÃO está disponível (RN04).");
                            return; // Encerra o método sem emprestar
                        }
                    } else {
                        System.out.println("ERRO: Equipamento inexistente.");
                        return;
                    }
                }
            }

            con.setAutoCommit(false); // Desliga o autossave para garantir as duas operações juntas

            try (PreparedStatement stmtEmp = con.prepareStatement(sqlEmprestimo);
                 PreparedStatement stmtEquip = con.prepareStatement(sqlAtualizaEquip)) {

                // Grava o empréstimo
                stmtEmp.setInt(1, emprestimo.getAluno().getId());
                stmtEmp.setInt(2, emprestimo.getEquipamento().getId());
                stmtEmp.executeUpdate();

                // Atualiza o equipamento para indisponível
                stmtEquip.setInt(1, emprestimo.getEquipamento().getId());
                stmtEquip.executeUpdate();

                con.commit();
                System.out.println("SUCESSO: Empréstimo realizado e equipamento atualizado!");

            } catch (SQLException e) {
                con.rollback();
                System.err.println("Erro na transação. Operação desfeita: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }
    }

    public void registrarDevolucao(int idEmprestimo, int idEquipamento) {
        String sqlDevolucao = "UPDATE emprestimo SET data_devolucao = NOW(), status = 'FINALIZADO' WHERE id = ?";
        String sqlAtualizaEquip = "UPDATE equipamento SET disponivel = true WHERE id = ?";

        try (Connection con = ConexaoMySQL.getConexao()) {
            con.setAutoCommit(false); // Transação para devolução (Regra 7.3)

            try (PreparedStatement stmtDev = con.prepareStatement(sqlDevolucao);
                 PreparedStatement stmtEquip = con.prepareStatement(sqlAtualizaEquip)) {

                stmtDev.setInt(1, idEmprestimo);
                stmtDev.executeUpdate();

                stmtEquip.setInt(1, idEquipamento);
                stmtEquip.executeUpdate();

                con.commit();
                System.out.println("SUCESSO: Devolução registrada e equipamento liberado!");

            } catch (SQLException e) {
                con.rollback();
                System.err.println("Erro ao devolver. Operação desfeita: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Erro de conexão na devolução: " + e.getMessage());
        }
    }
    public void listarEmprestimosAtivos() {
        String sql = "SELECT id, id_aluno, id_equipamento, data_emprestimo FROM emprestimo WHERE status = 'ATIVO'";

        try (Connection con = ConexaoMySQL.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                System.out.println(" -> ID do Empréstimo: " + rs.getInt("id") +
                        " | ID Aluno: " + rs.getInt("id_aluno") +
                        " | ID Equipamento: " + rs.getInt("id_equipamento"));
            }
            if (!encontrou) {
                System.out.println("Nenhum empréstimo ativo no momento.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos: " + e.getMessage());
        }
    }
}
