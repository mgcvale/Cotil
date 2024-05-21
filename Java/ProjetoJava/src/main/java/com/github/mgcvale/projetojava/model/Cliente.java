package com.github.mgcvale.projetojava.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cliente extends Pessoa implements IPessoa, FieldProvider {

    private boolean isAssociado;
    private String cpf;

    public final int ID_ORDINAL = 0;
    public final int NOME_ORDINAL = 1;
    public final int IDADE_ORDINAL = 2;
    public final int ISASSOCIADO_ORDINAL = 3;
    public final int CPF_ORDINAL = 4;


    public Cliente(int id, String nome, int idade, boolean isAssociado, String cpf) {
        super(id, nome, idade);
        this.isAssociado = isAssociado;
        this.cpf = cpf;
    }

    public Cliente(List<String> args) {
        setNome(args.get(NOME_ORDINAL));
        setCpf(args.get(CPF_ORDINAL));

        try {
            setAssociado(Boolean.parseBoolean(args.get(ISASSOCIADO_ORDINAL)));
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Associacão inválida: " + args.get(ISASSOCIADO_ORDINAL));
        }
        try {
            setId(Integer.parseInt(args.get(ID_ORDINAL)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + args.get(ID_ORDINAL));
        }
        try {
            setIdade(Integer.parseInt(args.get(IDADE_ORDINAL)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Idade inválida: " + args.get(IDADE_ORDINAL));
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
