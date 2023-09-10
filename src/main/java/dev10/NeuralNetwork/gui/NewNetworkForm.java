package dev10.NeuralNetwork.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class NewNetworkForm extends JPanel {

    private final JPanel layersPanel = new JPanel();
    private final JSpinner optionsField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    private final JSpinner layersField = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));


    public NewNetworkForm() {
        setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new Label("New Network", Label.CENTER));

        add(new Label("Options"));
        add(optionsField);

        add(new Label("Layers"));
        layersField.addChangeListener(this::handleLayersChange);
        add(layersField);

        addLayer();
        add(new JScrollPane(layersPanel));

        Button saveButton = new Button("Save");
        saveButton.addActionListener(this::save);
        add(saveButton);

        Button exitButton = new Button("Exit");
        exitButton.addActionListener(this::exit);
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
        NetworkTab.layout.show(this.getParent(), "menu");
    }

    private void save(ActionEvent e) {
        int options = (int) optionsField.getValue();
        int[] layers = Arrays.stream(layersPanel.getComponents())
                .filter(c -> c instanceof JSpinner)
                .map(c -> (JSpinner) c)
                .mapToInt(c -> (int) c.getValue())
                .toArray();

        Map<String, int[]> data = new HashMap<>();
        data.put("Options", new int[]{options});
        data.put("Layers", layers);

        //networkController.newNetwork(data);
    }

    private void addLayer() {
        int i = layersPanel.getComponentCount()/2;
        layersPanel.add(new Label(String.format("%s", i + 1)));
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
