package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.controller.ClienteService;
import com.github.mgcvale.projetojava.controller.FuncionarioService;
import com.github.mgcvale.projetojava.controller.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.FieldProvider;

import java.io.IOException;

public class FuncionarioCrudView extends AbstractCrudView<FuncionarioService> {

    public FuncionarioCrudView() {
        try {
            serviceObject = JsonSerializer.importJson("Funcionario", FuncionarioService.class);
        } catch (IOException e) {
            serviceObject = new FuncionarioService();
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
            serviceObject = JsonSerializer.importJson("Funcionario", FuncionarioService.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.refreshTable();
    }

}
