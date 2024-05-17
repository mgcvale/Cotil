package com.github.mgcvale.projetojava.model;

import java.util.List;

public class Produto implements FieldProvider {

    private String nome;
    private String descricao;
    private int id;
    private double preco;
    private Cor cor;

    public Produto(int id, String nome, String descricao, double preco, Cor cor) {
        this.nome = nome;
        this.descricao = descricao;
        this.id = id;
        setPreco(preco);
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = Math.max(0, preco);
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", id=" + id +
                ", preco=" + preco +
                ", cor=" + cor +
                '}';
    }

    @Override
    public List<Object> getAllFields() {
        return List.of(id, nome, descricao, preco, cor);
    }
}
