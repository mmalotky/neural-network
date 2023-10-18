package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.AIConfig;
import dev10.NeuralNetwork.controllers.MapController;
import dev10.NeuralNetwork.controllers.NetworkController;
import dev10.NeuralNetwork.models.MapElement;
import dev10.NeuralNetwork.models.NetworkConfigurationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class RunScreen extends Screen {
    private final NetworkController networkController;
    private final MapController mapController;
    private final AIConfig ai;
    private final JLabel networkLabel = new JLabel();
    private final JLabel mapLabel = new JLabel();
    private final JSpinner iterationsField = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
    private boolean isRunning = false;
    private final JLabel runningLabel = new JLabel("Not Running");
    private boolean stopping = false;
    JPanel runPanel = new JPanel();
    JPanel dataPanel = new JPanel();
    List<Double> errorList = new ArrayList<>();

    public RunScreen(NetworkController networkController, MapController mapController) {
        this.networkController = networkController;
        this.mapController = mapController;
        this.ai = new AIConfig(mapController, networkController);
        refresh();

        add(new Title("Run Network"));
        networkLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(networkLabel);
        mapLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(mapLabel);
        runningLabel.setAlignmentX(CENTER_ALIGNMENT);
        runningLabel.setForeground(Color.RED);
        add(runningLabel);

        JPanel graphicsPanel = new JPanel();
        graphicsPanel.setPreferredSize(new Dimension(400,400));
        graphicsPanel.setLayout(new BoxLayout(graphicsPanel, BoxLayout.X_AXIS));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setPreferredSize(new Dimension(200, 400));
        graphicsPanel.add(dataPanel);

        BoxLayout runPanelLayout = new BoxLayout(runPanel, BoxLayout.Y_AXIS);
        runPanel.setLayout(runPanelLayout);
        graphicsPanel.add(new JScrollPane(runPanel));

        add(graphicsPanel);

        Field itField = new Field("Iterations", iterationsField);
        itField.setAlignmentX(CENTER_ALIGNMENT);
        itField.setMaximumSize(new Dimension(500, 20));
        add(itField);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(this::run);
        runButton.setAlignmentX(CENTER_ALIGNMENT);
        add(runButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(a -> stopping = true);
        stopButton.setAlignmentX(CENTER_ALIGNMENT);
        add(stopButton);
    }

    private void run(ActionEvent actionEvent) {
        if(isRunning || networkController.getNetworkId() == null || mapController.getMapID() == null) return;
        isRunning = true;
        Thread runThread = new Thread(this::runAction);
        runThread.start();
    }

    private void runAction() {
        int iterations = (int) iterationsField.getValue();
        for(int i = 0; i < iterations; i++) {
            if(refresh() && !stopping) {
                try {
                    ai.train();
                    Thread.sleep(500);
                } catch (NetworkConfigurationException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        stopping = false;
        isRunning = false;
        refresh();
    }

    public boolean refresh() {
        String networkId = networkController.getNetworkId();
        networkLabel.setText(String.format("Current Network: %s", networkId));
        String mapId = mapController.getMapID();
        mapLabel.setText(String.format("Current Map: %s", mapId));

        if(mapId != null) drawMap();

        runningLabel.setText(isRunning ? "Running" : "Not Running");
        runningLabel.setForeground(isRunning ? Color.GREEN : Color.RED);

        updateErrorList();

        return networkId != null && mapId != null;
    }

    private void drawMap() {
        runPanel.removeAll();

        boolean rangingX = true;
        for(int x = 0; rangingX; x++) {
            JPanel row = new JPanel();
            row.setLayout(new GridBagLayout());
            row.getInsets().set(0,0,0,0);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));

            boolean rangingY = true;
            for(int y = 0; rangingY; y++) {
                MapElement el = mapController.getCoordinatesElement(new int[]{x,y});
                if(el == null) {
                    rangingY = false;
                    if(y == 0) rangingX = false;
                    continue;
                }
                Icon icon = switch (el) {
                    case FLOOR -> MapIcon.FLOOR;
                    case WALL -> MapIcon.WALL;
                    case START -> MapIcon.START;
                    case END -> MapIcon.END;
                };

                CardLayout iconCard = new CardLayout(0,0);
                JPanel iconPanel = new JPanel(iconCard);

                JLabel iconLabel = new JLabel(icon);
                iconLabel.setIconTextGap(0);
                iconPanel.add(iconLabel, "unoccupied");

                JLabel occupiedLabel = new JLabel(MapIcon.POSITION);
                occupiedLabel.setIconTextGap(0);
                iconPanel.add(occupiedLabel, "occupied");

                iconCard.show(iconPanel, "unoccupied");
                row.add(iconPanel);
            }
            runPanel.add(row);
        }
        updatePosition();
        runPanel.updateUI();
    }

    private void updatePosition() {
        int[] coordinates = mapController.getCoordinates();
        JPanel row = (JPanel) runPanel.getComponent(coordinates[0]);
        JPanel iconPanel = (JPanel) row.getComponent(coordinates[1]);
        CardLayout iconCard = (CardLayout) iconPanel.getLayout();
        iconCard.show(iconPanel, "occupied");
    }

    private void updateErrorList() {
        double lastError = networkController.getError();
        if(lastError == 0) errorList.clear();
        else errorList.add(lastError);
        if(errorList.size() > 20) errorList.remove(0);

        dataPanel.removeAll();
        dataPanel.add(new JLabel(String.valueOf(lastError)));
    }
}
