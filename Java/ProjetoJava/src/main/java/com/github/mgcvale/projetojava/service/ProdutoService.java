package com.github.mgcvale.projetojava.service;
import com.github.mgcvale.projetojava.model.Cor;
import com.github.mgcvale.projetojava.model.Produto;
import static com.github.mgcvale.projetojava.model.Produto.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ProdutoService implements Serializable, Service<Produto> {

    private ArrayList<Produto> produtos = new ArrayList<Produto>();

    @Override
    public void add(Produto object) {
        produtos.add(object);
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
            return produto.getNome().equalsIgnoreCase(name);
        }).findFirst();
    }

    public List<Produto> findByName(String searchTerm) {
        return produtos.stream().filter(produto -> {
            return produto.getNome().toLowerCase().contains(searchTerm.toLowerCase());
        }).collect(Collectors.toList());
    }

    public List<Produto> findBy(String searchTerm, int fieldIndex) {
        List<Produto> produtoList = Collections.emptyList();
        switch(fieldIndex) {
            case ID_ORDINAL -> produtoList = produtos.stream().filter(produto -> Integer.toString(produto.getId()).contains(searchTerm)).toList();
            case NOME_ORDINAL -> produtoList = produtos.stream().filter(produto -> produto.getNome().contains(searchTerm)).toList();
            case DESCRICAO_ORDINAL -> produtoList = produtos.stream().filter(produto -> produto.getDescricao().contains(searchTerm)).toList();
            case PRECO_ORDINAL -> produtoList = produtos.stream().filter(produto -> Double.toString(produto.getPreco()).contains(searchTerm)).toList();
            case COR_ORDINAL -> produtoList = produtos.stream().filter(produto ->produto.getCor().name().contains(searchTerm)).toList();
        }
        return produtoList;
    }

    public double getAveragePrice() {
        AtomicReference<Double> runningSum = new AtomicReference<>((double) 0);//precisa ser singleton se n n da pra usar no lambda
        produtos.forEach(produto -> runningSum.updateAndGet(v -> new Double((double) (v + produto.getPreco()))));
        return runningSum.get() / produtos.size();
    }

    public int getAboveAverage() {
        double avg = getAveragePrice();
        return produtos.stream().filter(produto -> produto.getPreco() > avg).toList().size();
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
    public Class<Produto> getServiceClass() {
        return Produto.class;
    }
}
