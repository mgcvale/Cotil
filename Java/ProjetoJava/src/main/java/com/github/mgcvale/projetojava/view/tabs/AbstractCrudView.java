package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.service.Service;
import com.github.mgcvale.projetojava.view.dialogs.ObjectAdderDialog;
import com.github.mgcvale.projetojava.view.dialogs.ObjectCreationListener;
import com.github.mgcvale.projetojava.view.util.ClassifiedJTable;
import com.github.mgcvale.projetojava.view.util.ClassifiedJTableModel;
import com.github.mgcvale.projetojava.view.util.JTableUtils;
import com.github.mgcvale.projetojava.view.util.PlaceholderTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractCrudView<T extends Service<?>> extends JPanel implements ObjectCreationListener {
    protected JScrollPane scrollPane;
    protected ClassifiedJTable table;
    protected ClassifiedJTableModel tableModel;
    protected T serviceObject;
    protected JButton addBtn, removeBtn;
    protected PlaceholderTextField searchTf;
    protected JPanel searchPane, infoPane;
    protected DefaultTableCellRenderer leftRenderer;
    protected static boolean isObjectAdderDialogOpened = false;
    JComboBox<String> searchByCombo;

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

        //search by combo box
        searchByCombo = new JComboBox<>(new String[]{"Nome"});

        initializeTable();
        updateTable();
        addListeners();
    }

    protected void initializeTable() {
        //table model and scroll pane
        tableModel = new UneditableTableModel();

        table = new ClassifiedJTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JLabel headerLabels = (JLabel) table.getTableHeader().getDefaultRenderer();
        headerLabels.setHorizontalAlignment(SwingConstants.CENTER);

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(Color.BLACK, 1));

        //centered renderer
        leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        if(serviceObject == null) {
            throw new NullPointerException("The service object needs to be instanciated in the superclass!");
        }

        // create columns based on the names of the fields, as well as their types
        try {
            //array lists to store the columns that should be a little smaller
            ArrayList<Integer> smallColumnsIndexes = new ArrayList<>();
            ArrayList<Integer> mediumColumnsIndexes = new ArrayList<>();
            // list of the types of the pojo object class for accurate table sorting
            List<Object> typeInstances = serviceObject.getServiceClass().getDeclaredConstructor().newInstance().getFieldTypesAsInstance();

            for (String fieldName : serviceObject.getServiceClass().getDeclaredConstructor().newInstance().getFieldNames()) {
                tableModel.addColumn(fieldName);
                tableModel.setColumnClass(tableModel.getColumnCount()-1, typeInstances.get(tableModel.getColumnCount()-1).getClass());
                table.setColumnClass(tableModel.getColumnCount()-1, typeInstances.get(tableModel.getColumnCount()-1).getClass());

                if(fieldName.equals("idade") || fieldName.equals("id")) {
                    smallColumnsIndexes.add(table.getColumnCount()-1);
                }
                if(fieldName.equals("preco") || fieldName.equals("cor") || fieldName.equals("associado")) {
                    mediumColumnsIndexes.add(table.getColumnCount()-1);
                }
            }

            //make small columns small and medium columns medium
            smallColumnsIndexes.forEach(index -> table.getColumnModel().getColumn(index).setMaxWidth(56));
            mediumColumnsIndexes.forEach(index -> table.getColumnModel().getColumn(index).setMaxWidth(108));

            //center columns
            table.getColumnModel().getColumns().asIterator().forEachRemaining(tableColumn -> tableColumn.setCellRenderer(leftRenderer));

            //add sorter
            table.setAutoCreateRowSorter(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void layComponents() {
        //panels
        //search panel
        searchPane = new JPanel(new GridBagLayout());
        JPanel advSearchPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //advanced search pane
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        advSearchPane.add(searchTf, gbc);

        gbc.insets.left = 5;
        gbc.weightx = 0.2;
        gbc.gridx++;
        advSearchPane.add(searchByCombo, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        searchPane.add(advSearchPane, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy++;
        gbc.weighty=1;
        searchPane.add(scrollPane, gbc);

        gbc.insets.bottom = 10;
        gbc.insets.right = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        searchPane.add(addBtn, gbc);

        gbc.insets.right = 10;
        gbc.gridx++;
        searchPane.add(removeBtn, gbc);

        //info panel
        infoPane = new JPanel();
        infoPane.setLayout(new BorderLayout());
        JLabel warning = new JLabel("Nenhum " + serviceObject.getObjectName() + " selecionado!");
        warning.setHorizontalAlignment(SwingConstants.CENTER);
        infoPane.add(warning, BorderLayout.CENTER);
        infoPane.setBorder(new EmptyBorder(new Insets(8, 8, 8, 8)));

        //this panel
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridheight = 2;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 10, 10, 0);
        add(searchPane, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridheight = 1;
        gbc.insets.right = 10;
        gbc.insets = new Insets(10, 10, 10, 15);
        add(infoPane, gbc);
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

        table.getSelectionModel().addListSelectionListener(e -> updateInfoPanel(table.getSelectedRow()));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                infoPane.setMinimumSize(new Dimension(getWidth() / 4, 10000));
                repaint();
            }
        });

        addBtn.addActionListener(e -> {
            try {
                if (isObjectAdderDialogOpened)
                    return;
                ObjectAdderDialog dialog = new ObjectAdderDialog(serviceObject.getServiceClass().getDeclaredConstructor().newInstance());
                dialog.addObjectCreationListener(AbstractCrudView.this);
                dialog.createAndShowGUI();
                isObjectAdderDialogOpened = true;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

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

        if(objectIndex == -1) {// the selection is null

            //remove everything, add placeholder text and revalidate layout
            infoPane.removeAll();
            infoPane.setLayout(new BorderLayout());
            JLabel warning = new JLabel("Nenhum " + serviceObject.getObjectName() + " selecionado!");
            warning.setHorizontalAlignment(SwingConstants.CENTER);
            infoPane.add(warning, BorderLayout.CENTER);
            removeBtn.setEnabled(false);

            revalidate();
            return;
        }

        // create table fields and fieldnames, and empty layout
        String[] fields = JTableUtils.getRowValues(table, objectIndex);
        String[] fieldNames = JTableUtils.getTableTitles(table);
        infoPane.removeAll();
        infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));

        //add things in the grid layout
        for(int i=0; i<fields.length; i++) {
            JTextArea info = new JTextArea(fieldNames[i] + ": " + fields[i]);
            info.setLineWrap(true);
            info.setBorder(BorderFactory.createEmptyBorder());
            info.setBackground(UIManager.getColor("panel.background"));
            infoPane.add(info);
        }

        // set min size so that the layout manager doesn't make it tiny and it linebreaks like crazy, and revalidate layout
        infoPane.setMinimumSize(new Dimension(getWidth()/4, getHeight()));
        removeBtn.setEnabled(true);
        revalidate();
    }

    public Class<? extends Service> getServiceClass() {
        return serviceObject.getClass();
    }

    public Service getService() {
        return serviceObject;
    }

    private static class UneditableTableModel extends ClassifiedJTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    @Override
    public void objectCreationExited() {
        isObjectAdderDialogOpened = false;
    }
}
