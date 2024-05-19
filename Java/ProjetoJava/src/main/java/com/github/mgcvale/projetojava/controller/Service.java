package com.github.mgcvale.projetojava.controller;

import com.github.mgcvale.projetojava.model.FieldProvider;
import com.github.mgcvale.projetojava.model.Funcionario;

import java.util.ArrayList;

public interface Service<T extends FieldProvider> {
    String getObjectName();
    Class<? extends FieldProvider> getServiceClass();
    ArrayList<T> getAll();
    void add(T object);
}
