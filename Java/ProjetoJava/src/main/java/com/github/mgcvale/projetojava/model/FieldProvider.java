package com.github.mgcvale.projetojava.model;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public interface FieldProvider {
    List<Object> getAllFields();
    List<String> getFieldNames();
    List<Object> getFieldTypesAsInstance();
}
