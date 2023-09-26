package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewMapForum extends Screen {
    private final MapController controller;
    private final MapTab tab;
    private final JSpinner heightField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    private final JSpinner widthField = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    private final JPanel mapField = new JPanel();
    public NewMapForum(MapController controller, MapTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new Title("New Map"));

        heightField.addChangeListener(this::changeRows);
        add(new Field("Height", heightField));

        widthField.addChangeListener(this::changeColumns);
        add(new Field("Width", widthField));

        JPanel row = new JPanel();
        row.add(new MapSegment());
        mapField.setLayout(new BoxLayout(mapField, BoxLayout.Y_AXIS));
        mapField.add(row);
        add(mapField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::save);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exit);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(exitButton);
    }

    private void changeRows(ChangeEvent changeEvent) {
        int value = (int) heightField.getValue();
        int rows = mapField.getComponentCount();
        while(rows != value) {
            if(rows > value) {
                mapField.remove(rows - 1);
                --rows;
            } else {
                int cols = (int) widthField.getValue();
                JPanel row = new JPanel();
                for(int i = 0; i < cols; i++) {
                    row.add(new MapSegment());
                }
                mapField.add(row);
                ++rows;
            }
        }
        mapField.updateUI();
    }

    private void changeColumns(ChangeEvent changeEvent) {
        int value = (int) widthField.getValue();
        for(Component component : mapField.getComponents()) {
            JPanel row = (JPanel) component;
            int cols = row.getComponentCount();

            while(cols != value) {
                if(cols > value) {
                    row.remove(cols - 1);
                    --cols;
                } else {
                    row.add(new MapSegment());
                    ++cols;
                }
            }
        }
        mapField.updateUI();
    }

    private void exit(ActionEvent actionEvent) {
        tab.navigate(MapTab.MENU);
    }

    private void save(ActionEvent actionEvent) {
    }
}
