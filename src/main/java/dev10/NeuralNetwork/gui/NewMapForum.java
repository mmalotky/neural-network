package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewMapForum extends Screen {
    private final MapController controller;
    private final MapTab tab;
    private final JSpinner heightField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    private final JSpinner widthField = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    public NewMapForum(MapController controller, MapTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new Title("New Map"));

        add(new Field("Height", heightField));

        add(new Field("Width", widthField));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::save);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exit);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(exitButton);
    }

    private void exit(ActionEvent actionEvent) {
        tab.navigate(MapTab.MENU);
    }

    private void save(ActionEvent actionEvent) {
    }
}
