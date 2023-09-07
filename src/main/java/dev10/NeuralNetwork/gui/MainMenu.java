package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel implements GuiMenu {
    public MainMenu() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new GridLayout());

        this.add(new Label("Main Menu"));

        Button networksButton = new Button("Networks");
        networksButton.addActionListener(this::navigate);
        this.add(networksButton);

        Button mapsButton = new Button("Maps");
        mapsButton.addActionListener(this::navigate);
        this.add(mapsButton);

        Button runButton = new Button("Run");
        runButton.addActionListener(this::navigate);
        this.add(runButton);
    }
}
