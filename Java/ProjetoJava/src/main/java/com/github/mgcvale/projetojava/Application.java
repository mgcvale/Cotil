package com.github.mgcvale.projetojava;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.github.mgcvale.projetojava.view.MainFrame;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch(UnsupportedLookAndFeelException ignored) {}

        SwingUtilities.invokeLater(() -> {
            new MainFrame().createAndShowGUI();
        });
    }
}
