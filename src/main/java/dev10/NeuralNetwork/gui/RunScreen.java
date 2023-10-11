package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.AIConfig;
import dev10.NeuralNetwork.controllers.MapController;
import dev10.NeuralNetwork.controllers.NetworkController;
import dev10.NeuralNetwork.models.MapElement;
import dev10.NeuralNetwork.models.NetworkConfigurationException;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RunScreen extends Screen {
    private final NetworkController networkController;
    private final MapController mapController;
    private final AIConfig ai;
    private final JLabel networkLabel = new JLabel();
    private final JLabel mapLabel = new JLabel();
    private final JTable mapTable = new JTable();

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

        runPanel.add(mapTable);
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

        if(mapId != null) drawMap();

        return networkId != null && mapId != null;
    }

    private void drawMap() {
        mapTable.removeAll();
        boolean rangingY = true;
        for(int y = 0; rangingY; y++) {
            boolean rangingX = true;
            for(int x = 0; rangingX; x++) {
                int cols = mapTable.getColumnCount();
                if(x < cols - 1) mapTable.addColumn(new TableColumn());

                MapElement el = mapController.getCoordinatesElement(new int[]{x,y});
                if(el == null) {
                    rangingX = false;
                    if(x == 0) rangingY = false;
                    continue;
                }

                Icon icon = switch (el) {
                    case FLOOR -> MapIcon.FLOOR;
                    case WALL -> MapIcon.WALL;
                    case START -> MapIcon.START;
                    case END -> MapIcon.END;
                };

                mapTable.add(new JLabel(icon));
            }
        }
        mapTable.updateUI();
    }
}
