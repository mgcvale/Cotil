package com.github.mgcvale.projetojava.controller;

import com.github.mgcvale.projetojava.model.Funcionario;
import com.github.mgcvale.projetojava.model.FieldProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FuncionarioService implements Serializable, Service<Funcionario> {

    protected ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();

    @Override
    public void add(Funcionario object) {
        funcionarios.add(object);
    }

    public void remove(Funcionario funcionario) {
        funcionarios.remove(funcionario);
    }

    public Optional<Funcionario> getById(int id) {
        return funcionarios.stream().filter(funcionario -> {
            return funcionario.getId() == id;
        }).findFirst();
    }

    public Optional<Funcionario> getByName(String name) {
        return funcionarios.stream().filter(funcionario -> {
           return funcionario.getNome().equalsIgnoreCase(name);
        }).findFirst();
    }

    public List<Funcionario> findByName(String searchTerm) {
        return funcionarios.stream().filter(funcionario -> {
            return funcionario.getNome().toLowerCase().contains(searchTerm.toLowerCase());
        }).collect(Collectors.toList());
    }

    public List<Funcionario> nameStartsWith(String searchTerm) {
        return funcionarios.stream().filter(funcionario -> {
            return funcionario.getNome().toLowerCase().startsWith(searchTerm.toLowerCase());
        }).collect(Collectors.toList());
    }

    @Override
    public ArrayList<Funcionario> getAll() {
        return funcionarios;
    }

    @Override
    public String getObjectName() {
        return "Funcionario";
    }

    @Override
    public Class<Funcionario> getServiceClass() {
        return Funcionario.class;
    }
}
