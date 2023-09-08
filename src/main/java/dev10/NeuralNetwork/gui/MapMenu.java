package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class MapMenu extends JPanel implements GuiNavigation {
    public MapMenu() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new Label(MAP_MENU, Label.CENTER));

        Button mainMenuButton = new Button(MAIN_MENU);
        mainMenuButton.addActionListener(this::navigate);
        this.add(mainMenuButton);
    }
}
