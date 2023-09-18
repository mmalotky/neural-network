package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MapMenu extends Screen {
    private final MapController controller;
    private final MapTab tab;

    public MapMenu(MapController controller, MapTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new Title("Maps"));

        JButton deleteButton = new JButton("Delete Map");
        deleteButton.addActionListener(this::deleteMap);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        JButton loadButton = new JButton("Load Map");
        loadButton.addActionListener(this::loadMap);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(loadButton);

        JButton newMapButton = new JButton("New Map");
        newMapButton.addActionListener(this::newMap);
        newMapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newMapButton);
    }

    private void newMap(ActionEvent actionEvent) {
        tab.navigate(MapTab.FORUM);
    }

    private void loadMap(ActionEvent actionEvent) {
    }

    private void deleteMap(ActionEvent actionEvent) {
    }

    public void refresh() {
    }
}
