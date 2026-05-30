package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.ConexaoMySQL;
import br.edu.ifpa.laboratorio.model.Equipamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {

    public void cadastrar(Equipamento equipamento) {
        String sql = "INSERT INTO equipamento (descricao, disponivel) VALUES (?, ?)";

        try (Connection con = ConexaoMySQL.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, equipamento.getDescricao());
            stmt.setBoolean(2, equipamento.isDisponivel());

            stmt.executeUpdate();
            System.out.println("Equipamento cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar equipamento: " + e.getMessage());
        }
    }

    public List<Equipamento> listarDisponiveis() {
        List<Equipamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipamento WHERE disponivel = true";

        try (Connection con = ConexaoMySQL.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipamento eq = new Equipamento();
                eq.setId(rs.getInt("id"));
                eq.setDescricao(rs.getString("descricao"));
                eq.setDisponivel(rs.getBoolean("disponivel"));
                lista.add(eq);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar equipamentos: " + e.getMessage());
        }
        return lista;
    }
}
