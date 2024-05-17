package com.github.mgcvale.projetojava.controller;

import com.github.mgcvale.projetojava.model.FieldProvider;

import java.util.ArrayList;

public interface Service {
    String getObjectName();
    Class<? extends FieldProvider> getServiceClass();
    ArrayList<? extends FieldProvider> getAll();

}
