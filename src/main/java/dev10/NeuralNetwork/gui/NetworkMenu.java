package dev10.NeuralNetwork.gui;


import javax.swing.*;
import java.awt.*;

public class NetworkMenu extends JPanel implements GuiMenu {
    public NetworkMenu() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new GridLayout());
        this.add(new Label("Networks"));

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.addActionListener(this::navigate);
        this.add(mainMenuButton);
    }

}
