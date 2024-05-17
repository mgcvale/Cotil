package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.view.util.PlaceholderTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class AbstractCrudView<Service> {
    protected JScrollPane scrollPane;
    protected JTable table;
    protected UneditableTableModel tableModel;
    protected Service serviceObject;
    protected JButton addBtn, removeBtn, advancedSearchBtn, customFilterBtn;
    protected PlaceholderTextField searchTf;
    protected JPanel searchPanel, rightPanel;

    public AbstractCrudView() {
        initComponents();
    }

    protected void initComponents() {
        //addBtn
        addBtn = new JButton("Adicionar");

        //removeBtn
        removeBtn = new JButton("Remover");

        //advancedSearchBtn
        advancedSearchBtn = new JButton("Pesquisa Avancada");

        //customFilterBtn
        customFilterBtn = new JButton("Filtros customizados");

        //searchTf
        searchTf = new PlaceholderTextField();
        searchTf.setPlaceholder("Search");

        //table
        tableModel = new UneditableTableModel();

        table = new JTable(tableModel);
        JLabel headerLabels = (JLabel) table.getTableHeader().getDefaultRenderer();
        headerLabels.setHorizontalAlignment(SwingConstants.CENTER);

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 1));
    }

    protected void layComponents() {

    }

    protected void updateTable() {

    }

    private static class UneditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


}
