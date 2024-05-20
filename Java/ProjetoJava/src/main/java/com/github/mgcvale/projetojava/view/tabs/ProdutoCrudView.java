package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.model.Cliente;
import com.github.mgcvale.projetojava.model.Funcionario;
import com.github.mgcvale.projetojava.model.Produto;
import com.github.mgcvale.projetojava.service.ProdutoService;
import com.github.mgcvale.projetojava.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.FieldProvider;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class ProdutoCrudView extends AbstractCrudView<ProdutoService> {

    public ProdutoCrudView() {
        try {
            serviceObject = JsonSerializer.importJson("Produto", ProdutoService.class);
        } catch (IOException e) {
            serviceObject = new ProdutoService();
            e.printStackTrace();
        }
        if(serviceObject == null)
            serviceObject = new ProdutoService();
        super.initAll();
        addListeners();
    }

    @Override
    protected void layComponents() {
        super.layComponents();

        JLabel avgPriceLabel = new JLabel("Preço Médio: ");
        JLabel greaterAvgPriceLabel = new JLabel("Acima do Preço Médio: ");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 0);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weighty = 0;
        gbc.weightx = 0.5;
        gbc.gridwidth = 1;
        searchPanel.add(avgPriceLabel, gbc);

        gbc.gridx++;
        searchPanel.add(greaterAvgPriceLabel, gbc);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        removeBtn.addActionListener(e -> {
            int selectedID = (int) table.getValueAt(table.getSelectedRow(), 0);
            Optional<Produto> obj = serviceObject.getById(selectedID);
            if(obj.isPresent()) {
                serviceObject.remove(obj.get());
            } else {
                JOptionPane.showMessageDialog(ProdutoCrudView.this,
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
    }

    @Override
    public void refreshTable() {
        try {
            serviceObject = JsonSerializer.importJson("Produto", ProdutoService.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.refreshTable();
    }

    @Override
    public void objectCreated(FieldProvider object) {
        serviceObject.add((Produto) object);
        updateTable(searchTf.getText());
    }

}
