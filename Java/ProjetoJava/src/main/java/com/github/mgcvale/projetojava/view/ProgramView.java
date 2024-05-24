package com.github.mgcvale.projetojava.view;

import com.github.mgcvale.projetojava.config.AppProperties;
import com.github.mgcvale.projetojava.config.ThemeManager;
import com.github.mgcvale.projetojava.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.view.tabs.AbstractCrudView;
import com.github.mgcvale.projetojava.view.tabs.ClienteCrudView;
import com.github.mgcvale.projetojava.view.tabs.FuncionarioCrudView;
import com.github.mgcvale.projetojava.view.tabs.SoftwareCrudView;
import com.github.mgcvale.projetojava.view.util.ThemeAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ProgramView extends JFrame {
    private ClienteCrudView clienteCrudView;
    private FuncionarioCrudView funcionarioCrudView;
    private SoftwareCrudView softwareCrudView;
    private JTabbedPane tabbedPane;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu preferencesMenu;
    private JMenu themeComboBox;
    private JMenuItem save;
    private JMenuItem refresh;
    private final JFrame thisJFrame = this;

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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        clienteCrudView = new ClienteCrudView();
        funcionarioCrudView = new FuncionarioCrudView();
        softwareCrudView = new SoftwareCrudView();
    }

    private void initMenu() {
        save = new JMenuItem("Salvar modificacões");
        refresh = new JMenuItem("Recarregar json");
        refresh.setToolTipText("Sobreescreverá os conteudos da aplicacao com os escritos nos arquivos JSON");

        preferencesMenu = new JMenu("Preferências");
        themeComboBox = new JMenu("Tema");
        fileMenu = new JMenu("Arquivo");
        fileMenu.add(save);
        fileMenu.add(refresh);

        ThemeAction.Observer observer = (action, value) -> {
            try {
                AppProperties properties = JsonSerializer.importJson("properties", AppProperties.class);
                properties.currentTheme = action.getValue();
                JsonSerializer.serializeObject(properties, "properties");
                ThemeManager.updateTheme(thisJFrame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        themeComboBox.add(new ThemeAction("Escuro mac", "macdark", observer));
        themeComboBox.add(new ThemeAction("Claro mac", "maclight", observer));
        themeComboBox.add(new ThemeAction("Padrão escuro", "flatdark", observer));
        themeComboBox.add(new ThemeAction("Padrao claro", "flatlight", observer));
        preferencesMenu.add(themeComboBox);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(preferencesMenu);
    }

    private void layComponents() {
        tabbedPane.addTab("Cliente", clienteCrudView);
        tabbedPane.addTab("Funcionario", funcionarioCrudView);
        tabbedPane.addTab("Software", softwareCrudView);
    }


    private void addListeners() {
        save.addActionListener(e -> {
            try {
                exportJsons();
                JOptionPane.showMessageDialog(ProgramView.this, "Dados salvos com sucesso.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ProgramView.this, "Não foi possível salvar os dados modificados.");
            }
        });

        refresh.addActionListener(e -> {
            ((AbstractCrudView<?>) tabbedPane.getSelectedComponent()).refreshTable();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    exportJsons();
                } catch (IOException ex) {
                    int result = JOptionPane.showConfirmDialog(ProgramView.this,
                            "Não foi possível salvar os dados modificados. Você deseja realmente sair e perder " +
                                    "as modificacões?");
                    if(result == JOptionPane.YES_OPTION) {
                        dispose();
                    } else {
                        super.windowClosing(e);
                        return;
                    }
                }
                dispose();
                super.windowClosing(e);
            }
        });
    }

    private void exportJsons() throws IOException {
        JsonSerializer.serializeService(clienteCrudView.getService());
        JsonSerializer.serializeService(softwareCrudView.getService());
        JsonSerializer.serializeService(funcionarioCrudView.getService());
    }

}
