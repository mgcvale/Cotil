package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.service.Service;
import com.github.mgcvale.projetojava.model.FieldProvider;
import com.github.mgcvale.projetojava.view.dialogs.ObjectAdderDialog;
import com.github.mgcvale.projetojava.view.dialogs.ObjectCreationListener;
import com.github.mgcvale.projetojava.view.util.JTableUtils;
import com.github.mgcvale.projetojava.view.util.PlaceholderTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class AbstractCrudView<T extends Service<?>> extends JPanel implements ObjectCreationListener {
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
        removeBtn.setEnabled(false);

        //searchTf
        searchTf = new PlaceholderTextField();
        searchTf.setPlaceholder("Search");

        //table
        tableModel = new UneditableTableModel();

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        infoPanel.setLayout(new BorderLayout());
        JLabel warning = new JLabel("Nenhum " + serviceObject.getObjectName() + " selecionado!");
        warning.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(warning, BorderLayout.CENTER);
        infoPanel.setBorder(new EmptyBorder(new Insets(8, 8, 8, 8)));

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
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridheight = 1;
        gbc.insets.right = 10;
        gbc.insets = new Insets(10, 10, 10, 15);
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

        table.getSelectionModel().addListSelectionListener(_ -> updateInfoPanel(table.getSelectedRow()));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                infoPanel.setMinimumSize(new Dimension(getWidth()/4, 10000));
                repaint();
            }
        });

        addBtn.addActionListener(_ -> {
            try {
                System.out.println("ALKSDJLAKJSD");
                ObjectAdderDialog dialog = new ObjectAdderDialog(serviceObject.getServiceClass().getDeclaredConstructor().newInstance());
                dialog.addObjectCreationListener(AbstractCrudView.this);
                dialog.createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
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

    public void refreshTable() {
        updateTable(searchTf.getText());
        System.out.println("refreshing");
    }

    protected void updateInfoPanel(int objectIndex) {
        System.out.println("updated: " + objectIndex);

        if(objectIndex == -1) { //selection is null
            infoPanel.removeAll();
            infoPanel.setLayout(new BorderLayout());
            JLabel warning = new JLabel("Nenhum " + serviceObject.getObjectName() + " selecionado!");
            warning.setHorizontalAlignment(SwingConstants.CENTER);
            infoPanel.add(warning, BorderLayout.CENTER);
            removeBtn.setEnabled(false);
            revalidate();
            repaint();
            return;
        }

        String[] fields = JTableUtils.getRowValues(table, objectIndex);
        String[] fieldNames = JTableUtils.getTableTitles(table);

        infoPanel.removeAll();
        infoPanel.setLayout(new GridLayout(fields.length, 1, 0, 0));

        for(int i=0; i<fields.length; i++) {
            JTextArea info = new JTextArea(fieldNames[i] + ": " + fields[i]);
            info.setLineWrap(true);
            info.setBorder(BorderFactory.createEmptyBorder());
            info.setBackground(UIManager.getColor("label.background"));
            infoPanel.add(info);
        }
        infoPanel.setMinimumSize(new Dimension(getWidth()/4, getHeight()));
        removeBtn.setEnabled(true);
        revalidate();
        repaint();
    }

    public Class<? extends Service> getServiceClass() {
        return serviceObject.getClass();
    }

    public Service getService() {
        return serviceObject;
    }

    private static class UneditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
