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

        add(new Label("New Map", Label.CENTER));

        add(new Label("Height"));
        add(heightField);

        add(new Label("Width"));
        add(widthField);

        Button saveButton = new Button("Save");
        saveButton.addActionListener(this::save);
        add(saveButton);

        Button exitButton = new Button("Exit");
        exitButton.addActionListener(this::exit);
        add(exitButton);
    }

    private void exit(ActionEvent actionEvent) {
        tab.navigate(MapTab.MENU);
    }

    private void save(ActionEvent actionEvent) {
    }
}
