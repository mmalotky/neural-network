package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.GuiController;

import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private final JPanel panel = new JPanel(new GridLayout());

    public MainMenu() {
        panel.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));

        Button networksButton = new Button("Networks");
        networksButton.addActionListener(GuiController::toNetworkMenu);
        panel.add(networksButton);

        Button mapsButton = new Button("Maps");
        mapsButton.addActionListener(GuiController::toMapMenu);
        panel.add(mapsButton);

        Button runButton = new Button("Run");
        runButton.addActionListener(GuiController::toRunScreen);
        panel.add(runButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
