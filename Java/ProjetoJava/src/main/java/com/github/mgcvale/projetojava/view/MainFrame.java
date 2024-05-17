package com.github.mgcvale.projetojava.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem save;
    private JMenuItem advancedSearch;
    private JMenuItem customFilter;

    public void createAndShowGUI() {
        initMenu();
        setJMenuBar(menuBar);
        setTitle("Sistema");
        setSize(screenSize.width/2, screenSize.height/2);
        setLocationRelativeTo(null);
        setContentPane(new ProgramView());
        setVisible(true);
    }

    private void initMenu() {
        customFilter = new JMenuItem("Filtro especial");
        advancedSearch = new JMenuItem("Busca avancada");
        save = new JMenuItem("Salvar modificacões");

        fileMenu = new JMenu("Arquivo");
        fileMenu.add(save);
        fileMenu.add(advancedSearch);
        fileMenu.add(customFilter);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
    }

}
