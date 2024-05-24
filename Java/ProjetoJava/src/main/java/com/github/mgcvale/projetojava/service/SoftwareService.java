package com.github.mgcvale.projetojava.service;
import com.github.mgcvale.projetojava.model.Software;
import static com.github.mgcvale.projetojava.model.Software.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SoftwareService implements Serializable, Service<Software> {

    private ArrayList<Software> softwares = new ArrayList<Software>();

    @Override
    public void add(Software object) {
        softwares.add(object);
    }

    public void remove(Software software) {
        softwares.remove(software);
    }

    public Optional<Software> getById(int id) {
        return softwares.stream().filter(software -> {
            return software.getId() == id;
        }).findFirst();
    }

    public Optional<Software> getByName(String name) {
        return softwares.stream().filter(software -> {
            return software.getNome().equalsIgnoreCase(name);
        }).findFirst();
    }

    public List<Software> findByName(String searchTerm) {
        return softwares.stream().filter(software -> {
            return software.getNome().toLowerCase().contains(searchTerm.toLowerCase());
        }).collect(Collectors.toList());
    }

    public List<Software> findBy(String searchTerm, int fieldIndex) {
        List<Software> softwareList = Collections.emptyList();
        switch(fieldIndex) {
            case ID_ORDINAL -> softwareList = softwares.stream().filter(software -> Integer.toString(software.getId()).contains(searchTerm)).toList();
            case NOME_ORDINAL -> softwareList = softwares.stream().filter(software -> software.getNome().contains(searchTerm)).toList();
            case DESCRICAO_ORDINAL -> softwareList = softwares.stream().filter(software -> software.getDescricao().contains(searchTerm)).toList();
            case PRECO_ORDINAL -> softwareList = softwares.stream().filter(software -> Double.toString(software.getPreco()).contains(searchTerm)).toList();
            case COR_ORDINAL -> softwareList = softwares.stream().filter(software -> software.getCor().name().contains(searchTerm)).toList();
        }
        return softwareList;
    }

    public double getAveragePrice() {
        AtomicReference<Double> runningSum = new AtomicReference<>((double) 0);//precisa ser singleton se n n da pra usar no lambda
        softwares.forEach(software -> runningSum.updateAndGet(v -> new Double((double) (v + software.getPreco()))));
        return runningSum.get() / softwares.size();
    }

    public int getAboveAverage() {
        double avg = getAveragePrice();
        return softwares.stream().filter(software -> software.getPreco() > avg).toList().size();
    }

    @Override
    public ArrayList<Software> getAll() {
        return softwares;
    }

    @Override
    public String getObjectName() {
        return "Software";
    }

    @Override
    public Class<Software> getServiceClass() {
        return Software.class;
    }
}
