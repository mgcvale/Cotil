package com.github.mgcvale.projetojava.view.dialogs;

import com.github.mgcvale.projetojava.model.FieldProvider;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ObjectAdderDialog extends JFrame {
    private FieldProvider objectInstance;
    private int dataQuantity;
    private int fieldsPerPage = 3;
    private int currentCard;

    private JButton addBtn, cancelBtn, nextBtn, prevBtn;
    private JLabel[] fieldLabels;
    private JTextField[] fieldTextFields;
    JPanel[] cards;

    public ObjectAdderDialog(FieldProvider provider) {
        objectInstance = provider;
        dataQuantity = provider.getFieldNames().size();
    }

    public void createAndShowGUI() {
        initComponents();
        layComponents();
        addListeners();

        setSize(420, 280);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        addBtn = new JButton("Adicionar");
        cancelBtn = new JButton("Cancelar");
        nextBtn = new JButton("próxima página");
        prevBtn = new JButton("página anterior");

        fieldLabels = new JLabel[dataQuantity];
        fieldTextFields = new JTextField[dataQuantity];
        for(int i=0; i<dataQuantity; i++) {
            fieldLabels[i] = new JLabel(objectInstance.getFieldNames().get(i) + ":");
            fieldTextFields[i] = new JTextField();
        }
        cards = new JPanel[dataQuantity/fieldsPerPage + 1];
    }

    private void layComponents() {
        setLayout(new CardLayout());
        int i;
        for(i=0; i<dataQuantity/fieldsPerPage + 1; i++) {
            cards[i] = new JPanel();
            layCardComponents(cards[i], i, i == dataQuantity/fieldsPerPage);
            System.out.println("i: " + i + "; isLastCard: " + (i==dataQuantity/fieldsPerPage));
            getContentPane().add(cards[i], Integer.toString(i));
        }
    }

    private void layCardComponents(JPanel card, int index, boolean isLastCard) {
        JPanel bottomPane = new JPanel(new BorderLayout());
        JPanel dataPane = new JPanel();

        //bottom pane
        if(index == 0) {
            bottomPane.add(cancelBtn, BorderLayout.WEST);
        } else {
            bottomPane.add(prevBtn, BorderLayout.WEST);
        }

        if(isLastCard) {
            bottomPane.add(addBtn, BorderLayout.EAST);
        } else {
            bottomPane.add(nextBtn, BorderLayout.EAST);
        }

        //data pane
        dataPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; gbc.gridx = 0; gbc.weightx = (double) 1/fieldsPerPage; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets.bottom = 16;
        gbc.insets.right = 24;
        for(int i=0; i<((isLastCard) ? dataQuantity%fieldsPerPage : fieldsPerPage); i++) {
            if(i == fieldsPerPage-1)
                gbc.insets.right = 0;
            dataPane.add(fieldLabels[fieldsPerPage*index + i], gbc);
            gbc.gridy++;
            dataPane.add(fieldTextFields[fieldsPerPage*index + i], gbc);
            gbc.gridy--;
            gbc.gridx++;
        }

        //add everything to card
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
        JLabel title = new JLabel("Adicionar " + objectInstance.getClass().getSimpleName());
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 16));
        card.add(title, BorderLayout.NORTH);
        card.add(dataPane, BorderLayout.CENTER);
        card.add(bottomPane, BorderLayout.SOUTH);
    }

    private void addListeners() {
        nextBtn.addActionListener(_ -> {
            currentCard++;
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), Integer.toString(currentCard));
        });
        prevBtn.addActionListener(_ -> {
            currentCard--;
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), Integer.toString(currentCard));
        });
        cancelBtn.addActionListener(_ -> dispose());
    }

}
