package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.controller.ClienteService;
import com.github.mgcvale.projetojava.controller.ProdutoService;
import com.github.mgcvale.projetojava.controller.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.FieldProvider;
import com.github.mgcvale.projetojava.model.Produto;

import java.io.IOException;

public class ProdutoCrudView extends AbstractCrudView<ProdutoService> {

    public ProdutoCrudView() {
        try {
            serviceObject = JsonSerializer.importJson("Produto", ProdutoService.class);
        } catch (IOException e) {
            serviceObject = new ProdutoService();
            e.printStackTrace();
        }
        super.initAll();
    }


    @Override
    protected void updateTable(String search) {
        if(serviceObject == null) {
            throw new NullPointerException("The service object needs to be instanciated in the superclass!");
        }

        System.out.println(search);

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

}
