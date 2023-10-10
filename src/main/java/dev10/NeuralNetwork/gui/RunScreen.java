package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.AIConfig;
import dev10.NeuralNetwork.controllers.MapController;
import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;

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

        add(new Title("Run Network"));
        add(networkLabel);
        add(mapLabel);

        refresh();
        add(runPanel);
    }

    public void refresh() {
        networkLabel.setText(String.format("Current Network: %s", networkController.getNetworkId()));
        mapLabel.setText(String.format("Current Map: %s", mapController.getMapID()));
    }
}
