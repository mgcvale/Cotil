package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.model.Cliente;
import com.github.mgcvale.projetojava.service.ClienteService;
import com.github.mgcvale.projetojava.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.FieldProvider;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class ClienteCrudView extends AbstractCrudView<ClienteService> {

    private JLabel youngerThanEighteenLabel;
    private JLabel olderThanSixtyLabel;
    private JLabel avgAgeLabel;

    private static final String YOUNGER_THAN_TXT = "Abaixo de 18 anos: ";
    private static final String OLDER_THAN_TXT = "Acima de 60 anos: ";
    private static final String AVG_AGE_TXT = "Idade média: ";

    public ClienteCrudView() {
        try {
            serviceObject = JsonSerializer.importServiceJson("Cliente", ClienteService.class);
        } catch (IOException e) {
            serviceObject = new ClienteService();
            e.printStackTrace();
        }
        if(serviceObject == null)
            serviceObject = new ClienteService();

        youngerThanEighteenLabel = new JLabel(YOUNGER_THAN_TXT, SwingConstants.CENTER);
        olderThanSixtyLabel = new JLabel(OLDER_THAN_TXT, SwingConstants.CENTER);
        avgAgeLabel = new JLabel(AVG_AGE_TXT, SwingConstants.CENTER);
        initAll();
    }

    @Override
    protected void layComponents() {
        super.layComponents();

        JPanel bottomPane = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 0);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        bottomPane.add(youngerThanEighteenLabel, gbc);

        gbc.gridx++;
        bottomPane.add(olderThanSixtyLabel, gbc);

        gbc.gridx++;
        bottomPane.add(avgAgeLabel, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        searchPane.add(bottomPane, gbc);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        removeBtn.addActionListener(e -> {
            int selectedID = (int) table.getValueAt(table.getSelectedRow(), 0);
            Optional<Cliente> obj = serviceObject.getById(selectedID);
            if(obj.isPresent()) {
                serviceObject.remove(obj.get());
            } else {
                JOptionPane.showMessageDialog(ClienteCrudView.this,
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
        for(FieldProvider object : serviceObject.findByName(search)) {
            tableModel.addRow(object.getAllFields().toArray());
        }
        avgAgeLabel.setText(AVG_AGE_TXT + String.format("%.2f", serviceObject.getAverageAge()));
        youngerThanEighteenLabel.setText(YOUNGER_THAN_TXT + serviceObject.getLowerThan18());
        olderThanSixtyLabel.setText(OLDER_THAN_TXT + serviceObject.getOlderThan60());
    }

    @Override
    public void refreshTable() {
        try {
            serviceObject = JsonSerializer.importServiceJson("Cliente", ClienteService.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.refreshTable();
    }

    @Override
    public void objectCreated(FieldProvider object) {
        serviceObject.add((Cliente) object);
        updateTable(searchTf.getText());
    }
}