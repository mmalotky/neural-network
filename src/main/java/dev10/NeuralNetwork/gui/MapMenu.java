package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class MapMenu extends Screen {

    private final JPanel mapsPanel = new JPanel();
    private final ButtonGroup mapsGroup = new ButtonGroup();
    private final MapController controller;
    private final MapTab tab;

    public MapMenu(MapController controller, MapTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new Title("Maps"));

        mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
        refresh();
        add(new JScrollPane(mapsPanel));

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
        String selected = getSelection();
        controller.loadMap(selected);
        refresh();
    }

    private void deleteMap(ActionEvent actionEvent) {
        String selected = getSelection();
        controller.deleteMap(selected);
        tab.refresh();
    }

    public void refresh() {
        Arrays.stream(mapsPanel.getComponents())
                .filter(b -> b instanceof AbstractButton)
                .forEach(mapsPanel::remove);
        mapsPanel.removeAll();

        List<String> maps = controller.getMapIds().getBody();
        if(maps == null || maps.size() == 0) {
            JLabel message = new JLabel("No Saved Maps");
            message.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            mapsPanel.add(message);
        }
        else {
            for(String map : maps) {
                String status = (map.equals(controller.getMapID())) ? "Active" : "";
                JRadioButton button = new JRadioButton(map + "     " + status);
                button.setActionCommand(map);
                mapsPanel.add(button);
                mapsGroup.add(button);
            }
        }
        mapsPanel.updateUI();
    }

    private String getSelection() {
        ButtonModel selection = mapsGroup.getSelection();
        return selection != null ? selection.getActionCommand() : null;
    }
}
