package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.model.Cliente;
import com.github.mgcvale.projetojava.model.Funcionario;
import com.github.mgcvale.projetojava.service.ClienteService;
import com.github.mgcvale.projetojava.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.FieldProvider;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class ClienteCrudView extends AbstractCrudView<ClienteService> {

    public ClienteCrudView() {

        try {
            serviceObject = JsonSerializer.importJson("Cliente", ClienteService.class);
        } catch (IOException e) {
            serviceObject = new ClienteService();
            e.printStackTrace();
        }
        if(serviceObject == null)
            serviceObject = new ClienteService();
        initAll();
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
    }

    @Override
    public void refreshTable() {
        try {
            serviceObject = JsonSerializer.importJson("Cliente", ClienteService.class);
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
