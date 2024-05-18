package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.controller.ClienteService;
import com.github.mgcvale.projetojava.controller.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.Cliente;
import com.github.mgcvale.projetojava.model.FieldProvider;
import com.github.mgcvale.projetojava.view.util.JTableUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ClienteCrudView extends AbstractCrudView<ClienteService> {

    public ClienteCrudView() {
        try {
            serviceObject = JsonSerializer.importJson("Cliente", ClienteService.class);
        } catch (IOException e) {
            serviceObject = new ClienteService();
            e.printStackTrace();
        }
        initAll();
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
}
