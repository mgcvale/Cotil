package com.github.mgcvale.projetojava.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public void createAndShowGUI() {
        setTitle("Sistema");
        setSize(screenSize.width/2, screenSize.height/2);
        setLocationRelativeTo(null);
        setContentPane(new ProgramView());
        setVisible(true);
    }

}
