package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.controller.Service;
import com.github.mgcvale.projetojava.model.FieldProvider;
import com.github.mgcvale.projetojava.view.util.PlaceholderTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractCrudView<T extends Service> extends JPanel {
    protected JScrollPane scrollPane;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected T serviceObject;
    protected JButton addBtn, removeBtn;
    protected PlaceholderTextField searchTf;
    protected JPanel searchPanel, infoPanel;
    protected FieldProvider selectedObject = null;

    protected void initAll() {
        initComponents();
        layComponents();
    }

    protected void initComponents() {
        //addBtn
        addBtn = new JButton("Adicionar");

        //removeBtn
        removeBtn = new JButton("Remover");

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

        initializeTable();
        updateTable();
        addListeners();
    }

    protected void layComponents() {
        //panels
        //search panel
        searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        searchPanel.add(searchTf, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy++;
        gbc.weighty=1;
        searchPanel.add(scrollPane, gbc);

        gbc.insets.bottom = 10;
        gbc.insets.right = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        searchPanel.add(addBtn, gbc);

        gbc.insets.right = 10;
        gbc.gridx++;
        searchPanel.add(removeBtn, gbc);

        //info panel
        infoPanel = new JPanel();
        if(selectedObject != null) {
            // add stuff dinamically
        } else {
            infoPanel.setLayout(new GridLayout(1, 1));
            JLabel warning = new JLabel("Nenhum objeto selecionado!");
            warning.setHorizontalAlignment(SwingConstants.CENTER);
            infoPanel.add(warning);
        }

        //this panel
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridheight = 2;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 10, 10, 0);
        add(searchPanel, gbc);

        gbc.gridx++;
        gbc.weightx = 0.2;
        gbc.weighty = 1;
        gbc.gridheight = 1;
        gbc.insets.right = 10;
        add(infoPanel, gbc);
    }

    protected void addListeners() {
        searchTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable(searchTf.getText());
            }
        });
    }

    protected void initializeTable() {
        if(serviceObject == null) {
            throw new NullPointerException("The service object needs to be instanciated in the superclass!");
        }
        try {
            ArrayList<Integer> smallColumnsIndexes = new ArrayList<>();
            ArrayList<Integer> mediumColumnsIndexes = new ArrayList<>();
            for (String fieldName : serviceObject.getServiceClass().getDeclaredConstructor().newInstance().getFieldNames()) {
                tableModel.addColumn(fieldName);
                if(fieldName.equals("idade") || fieldName.equals("id")) {
                    smallColumnsIndexes.add(table.getColumnCount()-1);
                }
                if(fieldName.equals("preco") || fieldName.equals("cor") || fieldName.equals("associado")) {
                    mediumColumnsIndexes.add(table.getColumnCount()-1);
                }
            }

            //make small columns small and medium columns medium
            smallColumnsIndexes.forEach(index -> table.getColumnModel().getColumn(index).setMaxWidth(48));
            mediumColumnsIndexes.forEach(index -> table.getColumnModel().getColumn(index).setMaxWidth(108));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateTable() {
        updateTable("");
    }

    protected abstract void updateTable(String search);

    public Class<? extends Service> getService() {
        return serviceObject.getClass();
    }

    private static class UneditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
