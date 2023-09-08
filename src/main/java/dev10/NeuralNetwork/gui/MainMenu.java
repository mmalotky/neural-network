package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel implements GuiNavigation {
    public MainMenu() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new Label(MAIN_MENU, Label.CENTER));

        Button networksButton = new Button(NETWORK_MENU);
        networksButton.addActionListener(this::navigate);
        this.add(networksButton);

        Button mapsButton = new Button(MAP_MENU);
        mapsButton.addActionListener(this::navigate);
        this.add(mapsButton);

        Button runButton = new Button(RUN_SCREEN);
        runButton.addActionListener(this::navigate);
        this.add(runButton);
    }
}
