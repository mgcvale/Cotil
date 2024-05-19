package com.github.mgcvale.projetojava.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Funcionario extends Pessoa implements IPessoa, FieldProvider {

    private double salario;
    private String senioridade;

    public Funcionario(int id, String nome, int idade, double salario, String senioridade) {
        super(id, nome, idade);
        setSalario(salario);
        this.senioridade = senioridade;
    }

    public Funcionario(List<String> args) {
        setNome(args.get(1));
        setSenioridade(args.get(4));

        try {
            setIdade(Integer.parseInt(args.get(2)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Idade inválida: " + args.get(2));
        }
        try {
            setId(Integer.parseInt(args.get(0)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido: " + args.get(0));
        }
        try {
            setSalario(Double.parseDouble(args.get(3)));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Salário inválido: " + args.get(3));
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
