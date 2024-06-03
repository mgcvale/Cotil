package com.github.mgcvale.projetojava.config;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.github.mgcvale.projetojava.serializer.JsonSerializer;

import javax.swing.*;
import java.io.IOException;

public class ThemeManager {

    public static void updateTheme(JFrame c) {
        try {
            AppProperties properties = JsonSerializer.importJson("properties", AppProperties.class);

            switch(properties.currentTheme) {
                case "macdark":
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());
                    break;
                case "maclight":
                    UIManager.setLookAndFeel(new FlatMacLightLaf());
                    break;
                case "flatdark":
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                case "flatlight":
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                default:
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ignored){}
        SwingUtilities.updateComponentTreeUI(c);
    }

}
