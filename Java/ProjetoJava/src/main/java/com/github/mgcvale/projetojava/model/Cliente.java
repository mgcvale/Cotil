package com.github.mgcvale.projetojava.model;

import java.util.Date;
import java.util.List;

public class Cliente extends Pessoa implements IPessoa, FieldProvider {

    private boolean isAssociado;
    private final Date dataCadastro;

    public Cliente(int id, String nome, int idade, boolean isAssociado, Date dataCadastro) {
        super(id, nome, idade);
        this.isAssociado = isAssociado;
        this.dataCadastro = dataCadastro;
    }

    public boolean isAssociado() {
        return isAssociado;
    }

    public void setAssociado(boolean associado) {
        isAssociado = associado;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    @Override
    public String exibir() {
        return toString();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }

    @Override
    public List<Object> getAllFields() {
        return List.of(nome, idade);
    }
}
