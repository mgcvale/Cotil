package com.github.mgcvale.projetojava.model;

public abstract class Pessoa {
    protected String nome;
    protected int idade;
    protected int id;

    public Pessoa(int id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        setIdade(idade);
    }

    public Pessoa() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = Math.max(0, idade);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
