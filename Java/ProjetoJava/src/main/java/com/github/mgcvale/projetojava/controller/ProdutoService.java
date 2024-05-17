package com.github.mgcvale.projetojava.controller;
import com.github.mgcvale.projetojava.model.FieldProvider;
import com.github.mgcvale.projetojava.model.Produto;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ProdutoService implements Serializable, Service {

    private ArrayList<Produto> produtos = new ArrayList<Produto>();

    public void add(Produto produto) {
        produtos.add(produto);
    }

    public void remove(Produto produto) {
        produtos.remove(produto);
    }

    public Optional<Produto> getById(int id) {
        return produtos.stream().filter(produto -> {
            return produto.getId() == id;
        }).findFirst();
    }

    public Optional<Produto> getByName(String name) {
        return produtos.stream().filter(produto -> {
            return Objects.equals(produto.getNome(), name);
        }).findFirst();
    }

    public List<Produto> findByName(String searchTerm) {
        return produtos.stream().filter(produto -> {
            return produto.getNome().contains(searchTerm);
        }).collect(Collectors.toList());
    }

    @Override
    public ArrayList<Produto> getAll() {
        return produtos;
    }

    @Override
    public String getObjectName() {
        return "Produto";
    }

    @Override
    public Class<? extends FieldProvider> getServiceClass() {
        return Produto.class;
    }
}
