package com.github.mgcvale.projetojava.controller;

import com.formdev.flatlaf.util.StringUtils;
import com.github.mgcvale.projetojava.model.Cliente;
import com.github.mgcvale.projetojava.model.FieldProvider;
import com.github.mgcvale.projetojava.model.Funcionario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClienteService implements Serializable, Service<Cliente> {

    private ArrayList<Cliente> clientes = new ArrayList<>();

    @Override
    public void add(Cliente object) {
        clientes.add(object);
    }

    public void remove(Cliente cliente) {
        clientes.remove(cliente);
    }

    public Optional<Cliente> getById(int id) {
        return clientes.stream().filter(cliente -> {
            return cliente.getId() == id;
        }).findFirst();
    }

    public Optional<Cliente> getByName(String name) {
        return clientes.stream().filter(cliente -> {
            return cliente.getNome().equalsIgnoreCase(name);
        }).findFirst();
    }

    public List<Cliente> findByName(String searchTerm) {
        return clientes.stream().filter(cliente -> {
            return cliente.getNome().toLowerCase().contains(searchTerm.toLowerCase());
        }).collect(Collectors.toList());
    }

    public List<Cliente> nameStartsWith(String searchTerm) {
        return clientes.stream().filter(cliente -> {
            return cliente.getNome().startsWith(searchTerm);
        }).collect(Collectors.toList());
    }

    @Override
    public ArrayList<Cliente> getAll() {
        return clientes;
    }

    @Override
    public String getObjectName() {
        return "Cliente";
    }

    @Override
    public Class<Cliente> getServiceClass() {
        return Cliente.class;
    }
}
