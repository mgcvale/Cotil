package com.github.mgcvale.projetojava.view;

import com.github.mgcvale.projetojava.view.dialogs.AdvancedSearchDialog;
import com.github.mgcvale.projetojava.view.tabs.ClienteCrudView;
import com.github.mgcvale.projetojava.view.tabs.FuncionarioCrudView;
import com.github.mgcvale.projetojava.view.tabs.ProdutoCrudView;

import javax.swing.*;
import java.awt.*;

public class ProgramView extends JFrame {
    private ClienteCrudView clienteCrudView;
    private FuncionarioCrudView funcionarioCrudView;
    private ProdutoCrudView produtoCrudView;
    private JTabbedPane tabbedPane;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem save;
    private JMenuItem advancedSearch;
    private JMenuItem customFilter;
    private JMenuItem refresh;

    public void createAndShowGUI() {
        initComponents();
        layComponents();
        initMenu();
        addListeners();
        setJMenuBar(menuBar);

        setTitle("Sistema");
        setSize(screenSize.width/2, screenSize.height/2);
        setLocationRelativeTo(null);
        setContentPane(tabbedPane);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        clienteCrudView = new ClienteCrudView();
        funcionarioCrudView = new FuncionarioCrudView();
        produtoCrudView = new ProdutoCrudView();
    }

    private void initMenu() {
        customFilter = new JMenuItem("Filtro especial");
        advancedSearch = new JMenuItem("Busca avancada");
        save = new JMenuItem("Salvar modificacões");
        refresh = new JMenuItem("Atualizar lista");

        fileMenu = new JMenu("Arquivo");
        fileMenu.add(save);
        fileMenu.add(advancedSearch);
        fileMenu.add(customFilter);
        fileMenu.add(refresh);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
    }

    private void layComponents() {
        tabbedPane.addTab("Cliente", clienteCrudView);
        tabbedPane.addTab("Funcionario", funcionarioCrudView);
        tabbedPane.addTab("Produto", produtoCrudView);
    }


    private void addListeners() {
        advancedSearch.addActionListener(_ -> new AdvancedSearchDialog().createAndShowGUI("Advanced Search"));
    }

}
