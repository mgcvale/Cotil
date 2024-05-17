package com.github.mgcvale.projetojava.view.tabs;

import com.github.mgcvale.projetojava.controller.ClienteService;
import com.github.mgcvale.projetojava.controller.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.model.Cliente;

import java.io.IOException;
import java.util.Calendar;

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
}
