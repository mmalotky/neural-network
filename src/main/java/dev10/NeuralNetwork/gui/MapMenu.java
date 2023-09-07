package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class MapMenu extends JPanel implements GuiMenu {
    public MapMenu() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new GridLayout());
        this.add(new Label("Maps"));

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.addActionListener(this::navigate);
        this.add(mainMenuButton);
    }
}
