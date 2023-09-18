package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class NewNetworkForum extends Screen {
    private final NetworkController controller;
    private final NetworkTab tab;
    private final JPanel layersPanel = new JPanel();
    private final JSpinner optionsField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    private final JSpinner layersField = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));


    public NewNetworkForum(NetworkController controller, NetworkTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new Title("New Network"));

        add(new Field("Options", optionsField));

        layersField.addChangeListener(this::handleLayersChange);
        add(new Field("Layers", layersField));

        addLayer();
        add(new JScrollPane(layersPanel));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::save);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exit);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(exitButton);
    }

    private void handleLayersChange(ChangeEvent changeEvent) {
        int value = (int) layersField.getValue();
        if(value > layersPanel.getComponentCount()/2) {
            while(value > layersPanel.getComponentCount()/2) addLayer();
        } else if (value < layersPanel.getComponentCount()/2) {
            while(value < layersPanel.getComponentCount()/2) removeLayer();
        }
    }

    private void exit(ActionEvent actionEvent) {
        tab.navigate(NetworkTab.MENU);
    }

    private void save(ActionEvent actionEvent) {
        int options = (int) optionsField.getValue();
        int[] layers = Arrays.stream(layersPanel.getComponents())
                .filter(c -> c instanceof JSpinner)
                .map(c -> (JSpinner) c)
                .mapToInt(c -> (int) c.getValue())
                .toArray();
        Map<String, int[]> data = new HashMap<>();
        data.put("options", new int[]{options});
        data.put("layers", layers);

        controller.newNetwork(data);
        tab.refresh();
        exit(actionEvent);
    }

    private void addLayer() {
        int i = layersPanel.getComponentCount()/2;
        layersPanel.add(new JLabel(String.format("%s", i + 1)));
        JSpinner layerField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
        layersPanel.add(layerField);
        layersPanel.updateUI();
    }

    private void removeLayer() {
        layersPanel.remove(layersPanel.getComponentCount() - 1);
        layersPanel.remove(layersPanel.getComponentCount() -1);
        layersPanel.updateUI();
    }
}
