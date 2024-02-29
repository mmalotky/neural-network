package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Form for creating a new Network
 */
public class NewNetworkForm extends Form {
    private final NetworkController controller;
    private final JPanel layersPanel = new JPanel();
    private final JSpinner optionsField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    private final JSpinner layersField = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));


    public NewNetworkForm(NetworkController controller, NetworkTab tab) {
        super(tab);
        this.controller = controller;

        add(new Title("New Network"));


        JPanel fields = new JPanel();
        fields.add(new Field("Options", optionsField));

        layersField.addChangeListener(this::handleLayersChange);
        fields.add(new Field("Layers", layersField));
        add(fields);

        JLabel nodesLabel = new JLabel("Nodes (from first to last)");
        nodesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(nodesLabel);
        addLayer();
        JScrollPane scroll = new JScrollPane(layersPanel);
        add(scroll);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::save);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exit);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(exitButton);
    }

    /**
     * Adjusts the number of fields in the layers panel by changes in the layers field
     * @param changeEvent ChangeEvent trigger
     */
    private void handleLayersChange(ChangeEvent changeEvent) {
        int value = (int) layersField.getValue();
        if(value > layersPanel.getComponentCount()/2) {
            while(value > layersPanel.getComponentCount()/2) addLayer();
        } else if (value < layersPanel.getComponentCount()/2) {
            while(value < layersPanel.getComponentCount()/2) removeLayer();
        }
    }

    @Override
    public void save(ActionEvent actionEvent) {
        int options = (int) optionsField.getValue();
        List<Integer> layersList = new ArrayList<>(Arrays.stream(layersPanel.getComponents())
                .filter(c -> c instanceof JSpinner)
                .map(c -> (JSpinner) c)
                .map(c -> (int) c.getValue())
                .toList());
        Collections.reverse(layersList);
        int[] layers = layersList.stream().mapToInt(Integer::valueOf).toArray();
        Map<String, int[]> data = new HashMap<>();
        data.put("options", new int[]{options});
        data.put("layers", layers);

        controller.newNetwork(data);
        tab.refresh();
        exit(actionEvent);
    }

    /**
     * Adds a layer to the layers panel
     */
    private void addLayer() {
        int i = layersPanel.getComponentCount()/2;
        layersPanel.add(new JLabel(String.format("%s", i + 1)));
        JSpinner layerField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
        layersPanel.add(layerField);
        layersPanel.updateUI();
    }

    /**
     * Removes a layer from the layers panel
     */
    private void removeLayer() {
        layersPanel.remove(layersPanel.getComponentCount() - 1);
        layersPanel.remove(layersPanel.getComponentCount() -1);
        layersPanel.updateUI();
    }

    @Override
    boolean refresh() {
        return true;
    }
}
