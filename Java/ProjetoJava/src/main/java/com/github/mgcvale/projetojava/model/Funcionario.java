package com.github.mgcvale.projetojava.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Funcionario extends Pessoa implements IPessoa, FieldProvider {

    private double salario;
    private final Date dataCadastro;

    public Funcionario(int id, String nome, int idade, double salario, Date dataCadastro) {
        super(id, nome, idade);
        setSalario(salario);
        this.dataCadastro = dataCadastro;
    }

    public Funcionario() {
        dataCadastro = Calendar.getInstance().getTime();
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = Math.max(0, salario);
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
        return "Funcionario{" +
                "salario=" + salario +
                ", dataCadastro=" + dataCadastro +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", id=" + id +
                '}';
    }

    @Override
    public List<Object> getAllFields() {
        return List.of(id, nome, idade, salario, dataCadastro);
    }

    @Override
    public List<String> getFieldNames() {
        return List.of("id", "nome", "idade", "salario", "cadastro");
    }
}
