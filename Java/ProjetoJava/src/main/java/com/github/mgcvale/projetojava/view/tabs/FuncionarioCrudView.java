package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.controller.ClienteService;
import com.github.mgcvale.projetojava.controller.FuncionarioService;
import com.github.mgcvale.projetojava.controller.serializer.JsonSerializer;

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

}
