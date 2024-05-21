package com.github.mgcvale.projetojava.view.util;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

public class ClassifiedJTableModel extends DefaultTableModel {
    HashMap<Integer, Class<?>> tableClasses;

    public ClassifiedJTableModel() {
        tableClasses = new HashMap<>();
    }

    public void setColumnClass(int i, Class<?> clazz) {
        tableClasses.put(i, clazz);
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return tableClasses.get(column);
    }

    @Override
    public Object getValueAt(int row, int column) {
        return super.getValueAt(row, column);
    }
}
