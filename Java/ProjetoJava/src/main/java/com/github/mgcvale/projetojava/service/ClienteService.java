package com.github.mgcvale.projetojava.service;

import com.github.mgcvale.projetojava.model.Cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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

    public double getAverageAge() {
        AtomicInteger runningAvg = new AtomicInteger();
        clientes.forEach(cliente -> runningAvg.addAndGet(cliente.getIdade()));
        return runningAvg.doubleValue()/clientes.size();
    }

    public int getLowerThan18() {
        return (clientes.stream().filter(cliente -> cliente.getIdade() < 18).toList()).size();
    }

    public int getOlderThan60() {
        return (clientes.stream().filter(cliente -> cliente.getIdade() > 60).toList()).size();
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
