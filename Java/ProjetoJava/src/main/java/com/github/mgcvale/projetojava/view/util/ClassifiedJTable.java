package com.github.mgcvale.projetojava.view.util;

import javax.swing.*;
import java.util.HashMap;

public class ClassifiedJTable extends JTable {

    HashMap<Integer, Class<?>> tableClasses;

    public ClassifiedJTable(ClassifiedJTableModel tableModel) {
        super(tableModel);
        tableClasses = new HashMap<>();
    }

    public ClassifiedJTable() {
        super();
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
