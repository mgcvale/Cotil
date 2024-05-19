package com.github.mgcvale.projetojava;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.github.mgcvale.projetojava.controller.ProdutoService;
import com.github.mgcvale.projetojava.model.Produto;
import com.github.mgcvale.projetojava.view.ProgramView;
import com.github.mgcvale.projetojava.view.dialogs.ObjectAdderDialog;

import javax.swing.*;
import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {

        //verify if the folder where the jsons will be sored exists; if not, create it and the empty jsons
        String jsonDir = System.getProperty("user.home") + "/JavaProjects/json";
        File jsonFolder = new File(jsonDir);
        String[] jsonNames = new String[]{"Cliente.json", "Produto.json", "Funcionario.json"};
        if(!jsonFolder.exists()) {
            jsonFolder.mkdirs();
        }
        for(String jsonName : jsonNames) {
            File jsonFile = new File(jsonDir + "/" + jsonName);
            if(!jsonFile.exists()) {
                try {
                    jsonFile.createNewFile();
                } catch (IOException e) {
                    System.err.println("Couldn't create json files! data may not be saved");
                }
            }
        }


        //set theme
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch(UnsupportedLookAndFeelException ignored) {}

        //create gui
        SwingUtilities.invokeLater(() -> {
            new ProgramView().createAndShowGUI();
            new ObjectAdderDialog(new Produto()).createAndShowGUI();
        });
    }
}
