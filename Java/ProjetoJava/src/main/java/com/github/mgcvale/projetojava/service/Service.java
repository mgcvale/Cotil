package com.github.mgcvale.projetojava.service;

import com.github.mgcvale.projetojava.model.FieldProvider;

import java.util.ArrayList;

public interface Service<T extends FieldProvider> {
    String getObjectName();
    Class<? extends FieldProvider> getServiceClass();
    ArrayList<T> getAll();
    void add(T object);
    void remove(T object);
}
