package com.github.mgcvale.projetojava.view;

import com.github.mgcvale.projetojava.view.tabs.ClienteCrudView;
import com.github.mgcvale.projetojava.view.tabs.FuncionarioCrudView;
import com.github.mgcvale.projetojava.view.tabs.ProdutoCrudView;

import javax.swing.*;

public class ProgramView extends JTabbedPane {
    private ClienteCrudView clienteCrudView;
    private FuncionarioCrudView funcionarioCrudView;
    private ProdutoCrudView produtoCrudView;

    public ProgramView() {
        initComponents();
        layComponents();
    }

    private void initComponents() {
        clienteCrudView = new ClienteCrudView();
        funcionarioCrudView = new FuncionarioCrudView();
        produtoCrudView = new ProdutoCrudView();
    }

    private void layComponents() {
        addTab("Cliente", clienteCrudView);
        addTab("Funcionario", funcionarioCrudView);
        addTab("Produto", produtoCrudView);
    }

}
