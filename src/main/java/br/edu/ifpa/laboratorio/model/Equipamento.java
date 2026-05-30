package br.edu.ifpa.laboratorio.model;

public class Equipamento {
    private int id;
    private String descricao;
    private boolean disponivel;

    public Equipamento() {
    }

    public Equipamento(int id, String descricao, boolean disponivel) {
        this.id = id;
        this.descricao = descricao;
        this.disponivel = disponivel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    @Override
    public String toString() {
        return id + " - " + descricao + " (Disponível: " + (disponivel ? "SIM" : "NÃO") + ")";
    }
}