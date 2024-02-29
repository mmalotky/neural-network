package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Menu component for selecting and managing active maps
 */
public class MapMenu extends Menu {
    private final MapController controller;

    public MapMenu(MapController controller, MapTab tab) {
        super(tab);
        this.controller = controller;

        add(new Title("Maps"));

        refresh();

        JButton deleteButton = new JButton("Delete Map");
        deleteButton.addActionListener(this::delete);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        JButton loadButton = new JButton("Load Map");
        loadButton.addActionListener(this::load);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(loadButton);

        JButton newMapButton = new JButton("New Map");
        newMapButton.addActionListener(this::create);
        newMapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newMapButton);
    }

    @Override
    public void edit(ActionEvent actionEvent) {
        System.out.println("Edit not Implemented in Map Menu");
    }

    @Override
    public void load(ActionEvent actionEvent) {
        String selected = getSelection();
        controller.loadMap(selected);
        refresh();
    }

    @Override
    public void delete(ActionEvent actionEvent) {
        String selected = getSelection();
        controller.deleteMap(selected);
        tab.refresh();
    }

    @Override
    public boolean refresh() {
        Arrays.stream(optionsPanel.getComponents())
                .filter(b -> b instanceof AbstractButton)
                .forEach(optionsPanel::remove);
        optionsPanel.removeAll();

        ResponseEntity<List<String>> mapGetter = controller.getMapIds();
        List<String> data = mapGetter.getBody();
        boolean success = mapGetter.getStatusCode() == HttpStatus.OK;

        if(data == null || data.size() == 0) {
            JLabel message = new JLabel("No Saved Maps");
            message.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            optionsPanel.add(message);
        }
        else if(!success) {
            String errors = String.join("\n", data);
            JLabel message = new JLabel("Error collecting data: \n" + errors);
            message.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            optionsPanel.add(message);
        }
        else {
            for(String map : data) {
                String status = (map.equals(controller.getMapID())) ? "Active" : "";
                JRadioButton button = new JRadioButton(map + "     " + status);
                button.setActionCommand(map);
                optionsPanel.add(button);
                buttonGroup.add(button);
            }
        }
        optionsPanel.updateUI();
        return success;
    }
}
