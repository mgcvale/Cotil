package com.github.mgcvale.projetojava;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.github.mgcvale.projetojava.config.AppProperties;
import com.github.mgcvale.projetojava.config.ThemeManager;
import com.github.mgcvale.projetojava.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.view.ProgramView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {

        //verify if the folder where the jsons will be sored exists; if not, create it and the empty jsons
        String jsonDir = System.getProperty("user.home") + "/JavaProjects/json";
        File jsonFolder = new File(jsonDir);
        String[] jsonNames = new String[]{"Cliente.json", "Software.json", "Funcionario.json", "properties.json"};
        if(!jsonFolder.exists()) {
            jsonFolder.mkdirs();
        }
        for(String jsonName : jsonNames) {
            File jsonFile = new File(jsonDir + "/" + jsonName);
            if(!jsonFile.exists()) {
                try {
                    jsonFile.createNewFile();
                    if(jsonFile.getName().equals(("properties.json"))) {
                        JsonSerializer.serializeObject(new AppProperties(), "properties");
                    }
                } catch (IOException e) {
                    System.err.println("Couldn't create json files! data may not be saved");
                }
            }
        }
        ThemeManager.updateTheme(new JFrame());

        //create gui
        SwingUtilities.invokeLater(() -> {
            new ProgramView().createAndShowGUI();
        });
    }
}
