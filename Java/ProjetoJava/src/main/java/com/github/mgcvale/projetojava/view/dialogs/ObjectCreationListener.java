package com.github.mgcvale.projetojava.view.dialogs;

import com.github.mgcvale.projetojava.model.FieldProvider;

public interface ObjectCreationListener {
    void objectCreated(FieldProvider object);
    void objectCreationExited();
}