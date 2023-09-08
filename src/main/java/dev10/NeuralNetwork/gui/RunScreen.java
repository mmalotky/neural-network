package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class RunScreen extends JPanel implements GuiNavigation {
    public RunScreen() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new GridLayout());
        this.add(new Label(RUN_SCREEN));

        Button mainMenuButton = new Button(MAIN_MENU);
        mainMenuButton.addActionListener(this::navigate);
        this.add(mainMenuButton);
    }
}
