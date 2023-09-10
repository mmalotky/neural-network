package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class RunScreen extends JPanel {
    public RunScreen() {
        setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        setLayout(new GridLayout());
        add(new Label("Run Network"));
    }
}
