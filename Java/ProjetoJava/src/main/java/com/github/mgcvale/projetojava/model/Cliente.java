package com.github.mgcvale.projetojava.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cliente extends Pessoa implements IPessoa, FieldProvider {

    private boolean isAssociado;
    private String cpf;

    public Cliente(int id, String nome, int idade, boolean isAssociado, String cpf) {
        super(id, nome, idade);
        this.isAssociado = isAssociado;
        this.cpf = cpf;
    }

    public Cliente(List<String> args) {
        setNome(args.get(1));
        setCpf(args.get(4));

        try {
            setAssociado(Boolean.parseBoolean(args.get(3)));
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Associacão inválida: " + args.get(3));
        }
        try {
            setId(Integer.parseInt(args.get(0)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + args.get(0));
        }
        try {
            setIdade(Integer.parseInt(args.get(2)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Idade inválida: " + args.get(2));
        }
    }

    public Cliente() {}

    public boolean isAssociado() {
        return isAssociado;
    }

    public void setAssociado(boolean associado) {
        isAssociado = associado;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String exibir() {
        return toString();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "isAssociado=" + isAssociado +
                ", cpf=" + cpf +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", id=" + id +
                '}';
    }

    @Override
    public List<Object> getAllFields() {
        return List.of(id, nome, idade, isAssociado, cpf);
    }

    @Override
    public List<String> getFieldNames() {
        return List.of("id", "nome", "idade", "associado", "cpf");
    }

    @Override
    public List<Object> getFieldTypesAsInstance() {
        return List.of(0, "", 0, false, "");
    }
}
