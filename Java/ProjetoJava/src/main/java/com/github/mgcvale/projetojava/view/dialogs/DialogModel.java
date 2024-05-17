package com.github.mgcvale.projetojava.view.dialogs;

import com.github.mgcvale.projetojava.view.tabs.ClienteCrudView;
import com.github.mgcvale.projetojava.view.tabs.FuncionarioCrudView;
import com.github.mgcvale.projetojava.view.tabs.ProdutoCrudView;

import javax.swing.*;
import java.awt.*;

public class DialogModel extends JFrame{
    private ClienteCrudView clienteCrudView;
    private FuncionarioCrudView funcionarioCrudView;
    private ProdutoCrudView produtoCrudView;
    private JTabbedPane tabbedPane;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public void createAndShowGUI(String title) {
        initComponents();
        layComponents();

        setTitle(title);
        setSize(screenSize.width/2, screenSize.height/2);
        setLocationRelativeTo(null);
        setContentPane(tabbedPane);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        clienteCrudView = new ClienteCrudView();
        funcionarioCrudView = new FuncionarioCrudView();
        produtoCrudView = new ProdutoCrudView();
    }

    private void layComponents() {
        tabbedPane.addTab("Cliente", clienteCrudView);
        tabbedPane.addTab("Funcionario", funcionarioCrudView);
        tabbedPane.addTab("Produto", produtoCrudView);
    }
}
