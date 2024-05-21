package com.github.mgcvale.projetojava.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Funcionario extends Pessoa implements IPessoa, FieldProvider {

    private double salario;
    private String senioridade;

    public static final int ID_ORDINAL          = 0;
    public static final int NOME_ORDINAL        = 1;
    public static final int IDADE_ORDINAL       = 2;
    public static final int SALARIO_ORDINAL     = 3;
    public static final int SENIORIDADE_ORDINAL = 4;

    public Funcionario(int id, String nome, int idade, double salario, String senioridade) {
        super(id, nome, idade);
        setSalario(salario);
        this.senioridade = senioridade;
    }

    public Funcionario(List<String> args) {
        setNome(args.get(NOME_ORDINAL));
        setSenioridade(args.get(SENIORIDADE_ORDINAL));

        try {
            setIdade(Integer.parseInt(args.get(IDADE_ORDINAL)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Idade inválida: " + args.get(IDADE_ORDINAL));
        }
        try {
            setId(Integer.parseInt(args.get(ID_ORDINAL)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + args.get(IDADE_ORDINAL));
        }
        try {
            setSalario(Double.parseDouble(args.get(SALARIO_ORDINAL)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Salário inválido: " + args.get(SALARIO_ORDINAL));
        }
    }

    public Funcionario() { }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = Math.max(0, salario);
    }

    public String getSenioridade() {
        return senioridade;
    }

    public void setSenioridade(String senioridade) {
        this.senioridade = senioridade;
    }

    @Override
    public String exibir() {
        return toString();
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "salario=" + salario +
                ", senioridade=" + senioridade +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", id=" + id +
                '}';
    }

    @Override
    public List<Object> getAllFields() {
        return List.of(id, nome, idade, salario, senioridade);
    }

    @Override
    public List<String> getFieldNames() {
        return List.of("id", "nome", "idade", "salario", "senioridade");
    }

    @Override
    public List<Object> getFieldTypesAsInstance() {
        return List.of(0, "", 0, 0d, "");
    }
}
