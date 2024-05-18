package com.github.mgcvale.projetojava.view.util;

import javax.swing.*;

public class JTableUtils {
    public static String[] getRowValues(JTable table, int rowIndex) {
        String[] result = new String[table.getColumnCount()];

        for(int i=0; i<table.getColumnCount(); i++) {
            result[i] = table.getValueAt(rowIndex, i).toString();
        }
        return result;
    }
    public static String[] getTableTitles(JTable table) {
        String[] result = new String[table.getColumnCount()];

        for(int i=0; i<table.getColumnCount(); i++) {
            result[i] = table.getColumnModel().getColumn(i).getHeaderValue().toString();
        }
        return result;
    }
}
