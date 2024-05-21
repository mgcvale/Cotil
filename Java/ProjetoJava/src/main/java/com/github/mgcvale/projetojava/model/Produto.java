package com.github.mgcvale.projetojava.model;

import com.github.mgcvale.projetojava.exception.InvalidColorException;

import java.io.Serializable;
import java.util.List;

public class Produto implements FieldProvider, Serializable {
    public final static int ID_ORDINAL = 0;
    public final static int NOME_ORDINAL = 1;
    public final static int DESCRICAO_ORDINAL = 2;
    public final static int PRECO_ORDINAL = 3;
    public final static int COR_ORDINAL = 4;

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

    public Produto(List<String> args) {
        setNome(args.get(NOME_ORDINAL));
        setDescricao(args.get(DESCRICAO_ORDINAL));
        try {
            setId(Integer.parseInt(args.get(ID_ORDINAL)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + args.get(ID_ORDINAL));
        }
        try {
            setPreco(Double.parseDouble(args.get(PRECO_ORDINAL)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Preco inválido: " + args.get(PRECO_ORDINAL));
        }
        try {
            setCor(Cor.valueOf(args.get(COR_ORDINAL)));
        } catch (IllegalArgumentException e) {
            throw new InvalidColorException("Cor inválida: " + args.get(COR_ORDINAL));
        }
    }

    public Produto(String id, String nome, String descricao, String preco, String cor) throws NumberFormatException, InvalidColorException {
        setNome(nome);
        setDescricao(descricao);
        try {
            setId(Integer.parseInt(id));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
        try {
            setPreco(Double.parseDouble(preco));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Preco inválido: " + preco);
        }
        try {
            setCor(Cor.valueOf(cor));
        } catch (IllegalArgumentException e) {
            throw new InvalidColorException("Cor inválida: " + cor);
        }
    }

    public Produto() {}

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

    @Override
    public List<String> getFieldNames() {
        return List.of("id", "nome", "descricao", "preco", "cor");
    }

    @Override
    public List<Object> getFieldTypesAsInstance() {
        return List.of(0, "", "", 0d, Cor.PRETO);
    }
}
