package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.model.Software;
import com.github.mgcvale.projetojava.service.SoftwareService;
import com.github.mgcvale.projetojava.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.FieldProvider;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SoftwareCrudView extends AbstractCrudView<SoftwareService> {

    private static final String AVG_PRICE_TXT = "Preço Médio: ";
    private static final String GREATEST_AVG_TXT = "Acima do Preço Médio: ";
    private JLabel avgPriceLabel;
    private JLabel greatestAvgPriceLabel;

    public SoftwareCrudView() {
        try {
            serviceObject = JsonSerializer.importServiceJson("Software", SoftwareService.class);
        } catch (IOException e) {
            serviceObject = new SoftwareService();
            e.printStackTrace();
        }
        if(serviceObject == null)
            serviceObject = new SoftwareService();
        avgPriceLabel = new JLabel(AVG_PRICE_TXT, SwingConstants.CENTER);
        greatestAvgPriceLabel = new JLabel(GREATEST_AVG_TXT, SwingConstants.CENTER);
        super.initAll();
        addListeners();
    }

    @Override
    protected void initComponents() {
        super.initComponents();
        searchByCombo = new JComboBox<>(new String[]{"Nome", "Descricao", "Preco", "Plataforma"});
    }

    @Override
    protected void layComponents() {
        super.layComponents();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 0);

        // bottom info
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        searchPane.add(avgPriceLabel, gbc);

        gbc.gridx++;
        searchPane.add(greatestAvgPriceLabel, gbc);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        removeBtn.addActionListener(e -> {
            int selectedID = (int) table.getValueAt(table.getSelectedRow(), 0);
            Optional<Software> obj = serviceObject.getById(selectedID);
            if(obj.isPresent()) {
                serviceObject.remove(obj.get());
            } else {
                JOptionPane.showMessageDialog(SoftwareCrudView.this,
                        "Parece que esta entrada não existe; tente atualizar a lista de entradas ou reiniciar o programa.");
            }
            updateTable();
        });
    }

    @Override
    protected void updateTable(String search) {
        if(serviceObject == null) {
            throw new NullPointerException("The service object needs to be instanciated in the superclass!");
        }

        tableModel.setRowCount(0);

        tableModel.setRowCount(0);
        List<Software> objects = serviceObject.getAll();
        if(!search.isEmpty()) {
            String selectedSearch = (String) searchByCombo.getSelectedObjects()[0];
            System.out.println("selected search: " + selectedSearch);
            switch (selectedSearch) {
                case "Nome" -> objects = serviceObject.findBy(search, Software.NOME_ORDINAL);
                case "Descricao" -> objects = serviceObject.findBy(search, Software.DESCRICAO_ORDINAL);
                case "Preco" -> objects = serviceObject.findBy(search, Software.PRECO_ORDINAL);
                case "Plataforma" -> objects = serviceObject.findBy(search, Software.COR_ORDINAL);
            }
            System.out.println(objects);
        }

        for(FieldProvider object : objects) {
            tableModel.addRow(object.getAllFields().toArray());
        }
        avgPriceLabel.setText(AVG_PRICE_TXT + String.format("%.2f", serviceObject.getAveragePrice()));
        greatestAvgPriceLabel.setText(GREATEST_AVG_TXT + serviceObject.getAboveAverage());
    }

    @Override
    public void refreshTable() {
        try {
            serviceObject = JsonSerializer.importServiceJson("Software", SoftwareService.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.refreshTable();
    }

    @Override
    public void objectCreated(FieldProvider object) {
        serviceObject.add((Software) object);
        updateTable(searchTf.getText());
    }
}
