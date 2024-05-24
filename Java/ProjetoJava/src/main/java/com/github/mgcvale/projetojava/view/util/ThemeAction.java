package com.github.mgcvale.projetojava.view.util;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ThemeAction extends AbstractAction {
    public static Observer Observer;
    public interface Observer{
        public void themeChanged(ThemeAction action, String value);
    }

    private final String value;
    private final Observer observer;

    public ThemeAction(String text, String value, Observer observer) {
        super(text);
        this.value = value;
        this.observer = observer;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(observer == null) {
            return;
        }
        observer.themeChanged(this, value);
    }
}
