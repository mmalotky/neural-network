package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private JPanel panel = new JPanel(new GridLayout());

    public MainMenu() {
        panel.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
    }

    public JPanel getPanel() {
        return panel;
    }
}
