package com.github.mgcvale.projetojava.view.util;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.util.Objects;

public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private boolean placeholderActive = true;
    private Color placeholderForeground = UIManager.getColor("TextField.inactiveForeground");
    private Color foreground = getForeground();

    public PlaceholderTextField(String placeholder) {
        this();
        this.placeholder = placeholder;
        setTextSuper(placeholder);
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
        focusLost();
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
                    placeholderActive = true;
                    setTextSuper(placeholder);
                    setForeground(placeholderForeground);
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

    @Override
    public String getText() {
        if(placeholderActive)
            return "";
        return super.getText();
    }

    private void focusLost() {
        if(getText().isEmpty()){
            placeholderActive = true;
            setTextSuper(placeholder);
            setForeground(placeholderForeground);
        }
    }

    public void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document)e.getOldValue();
            Document d2 = (Document)e.getNewValue();
            if (d1 != null) d1.removeDocumentListener(dl);
            if (d2 != null) d2.addDocumentListener(dl);
            dl.changedUpdate(null);
        });
        Document d = text.getDocument();
        if (d != null) d.addDocumentListener(dl);
    }

}
