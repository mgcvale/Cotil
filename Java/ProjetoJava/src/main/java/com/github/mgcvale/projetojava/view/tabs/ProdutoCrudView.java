package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.controller.ClienteService;
import com.github.mgcvale.projetojava.controller.ProdutoService;
import com.github.mgcvale.projetojava.controller.serializer.JsonSerializer;
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

}
