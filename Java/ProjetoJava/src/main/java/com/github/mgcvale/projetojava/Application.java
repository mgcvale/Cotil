package com.github.mgcvale.projetojava;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.github.mgcvale.projetojava.view.ProgramView;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {

        //set theme
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch(UnsupportedLookAndFeelException ignored) {}

        //create gui
        SwingUtilities.invokeLater(() -> {
            new ProgramView().createAndShowGUI();
        });
    }
}
