package com.github.mgcvale.projetojava.controller;

import com.github.mgcvale.projetojava.model.Cliente;
import com.github.mgcvale.projetojava.model.FieldProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClienteService implements Serializable, Service {

    private ArrayList<Cliente> clientes = new ArrayList<>();

    public void add(Cliente cliente) {
        clientes.add(cliente);
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
            return Objects.equals(cliente.getNome(), name);
        }).findFirst();
    }

    public List<Cliente> findByName(String searchTerm) {
        return clientes.stream().filter(cliente -> {
            return cliente.getNome().contains(searchTerm);
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
    public Class<? extends FieldProvider> getServiceClass() {
        return Cliente.class;
    }
}
