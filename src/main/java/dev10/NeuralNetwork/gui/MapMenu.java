package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;

import java.awt.*;
import java.awt.event.ActionEvent;

public class MapMenu extends Screen {
    private final MapController controller;
    private final MapTab tab;

    public MapMenu(MapController controller, MapTab tab) {
        this.controller = controller;
        this.tab = tab;

        Button deleteButton = new Button("Delete Map");
        deleteButton.addActionListener(this::deleteMap);
        add(deleteButton);

        Button loadButton = new Button("Load Map");
        loadButton.addActionListener(this::loadMap);
        add(loadButton);

        Button newMapButton = new Button("New Map");
        newMapButton.addActionListener(this::newMap);
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
