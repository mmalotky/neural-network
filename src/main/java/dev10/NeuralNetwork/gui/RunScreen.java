package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.AIConfig;
import dev10.NeuralNetwork.controllers.MapController;
import dev10.NeuralNetwork.controllers.NetworkController;
import dev10.NeuralNetwork.models.NetworkConfigurationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RunScreen extends Screen {
    private final NetworkController networkController;
    private final MapController mapController;
    private final AIConfig ai;
    private final JLabel networkLabel = new JLabel();
    private final JLabel mapLabel = new JLabel();

    JPanel runPanel = new JPanel();
    public RunScreen(NetworkController networkController, MapController mapController) {
        this.networkController = networkController;
        this.mapController = mapController;
        this.ai = new AIConfig(mapController, networkController);
        refresh();

        add(new Title("Run Network"));
        networkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(networkLabel);
        mapLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(mapLabel);

        add(runPanel);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(this::run);
        runButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(runButton);
    }

    private void run(ActionEvent actionEvent) {
        if(refresh()){
            try {
                ai.train();
            } catch (NetworkConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean refresh() {
        String networkId = networkController.getNetworkId();
        networkLabel.setText(String.format("Current Network: %s", networkId));
        String mapId = mapController.getMapID();
        mapLabel.setText(String.format("Current Map: %s", mapId));

        return networkId != null && mapId != null;
    }
}
