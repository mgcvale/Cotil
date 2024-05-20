package com.github.mgcvale.projetojava.view;

import com.github.mgcvale.projetojava.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.view.tabs.AbstractCrudView;
import com.github.mgcvale.projetojava.view.tabs.ClienteCrudView;
import com.github.mgcvale.projetojava.view.tabs.FuncionarioCrudView;
import com.github.mgcvale.projetojava.view.tabs.ProdutoCrudView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ProgramView extends JFrame {
    private ClienteCrudView clienteCrudView;
    private FuncionarioCrudView funcionarioCrudView;
    private ProdutoCrudView produtoCrudView;
    private JTabbedPane tabbedPane;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem save;
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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        clienteCrudView = new ClienteCrudView();
        funcionarioCrudView = new FuncionarioCrudView();
        produtoCrudView = new ProdutoCrudView();
    }

    private void initMenu() {
        save = new JMenuItem("Salvar modificacões");
        refresh = new JMenuItem("Atualizar lista");

        fileMenu = new JMenu("Arquivo");
        fileMenu.add(save);
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
        JsonSerializer.exportToJson(clienteCrudView.getService());
        JsonSerializer.exportToJson(produtoCrudView.getService());
        JsonSerializer.exportToJson(funcionarioCrudView.getService());
    }

}
