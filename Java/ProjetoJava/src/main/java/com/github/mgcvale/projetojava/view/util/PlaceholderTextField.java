package com.github.mgcvale.projetojava.view.util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;

public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private boolean placeholderActive = true;
    private Color placeholderForeground = UIManager.getColor("TextField.inactiveForeground");
    private Color foreground = getForeground();

    public PlaceholderTextField(String placeholder) {
        this();
        this.placeholder = placeholder;
    }

    public PlaceholderTextField() {
        this.placeholder = "";
        addLiteners();
        setForeground(placeholderForeground);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    private void addLiteners () {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(placeholderActive) {
                    setForeground(foreground);
                    setTextSuper("");
                    placeholderActive = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(getText().isEmpty()){
                    setTextSuper(placeholder);
                    setForeground(placeholderForeground);
                    placeholderActive = true;
                }
            }
        });
    }

    private void setTextSuper(String text) {
        super.setText(text);
    }

    @Override
    public void setText(String t) {
        super.setText(t);
        focusLost();
    }

    private void focusLost() {
        if(getText().isEmpty()){
            setTextSuper(placeholder);
            setForeground(placeholderForeground);
            placeholderActive = true;
        }
    }
}
