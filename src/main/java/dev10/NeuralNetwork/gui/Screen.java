package dev10.NeuralNetwork.gui;

import javax.swing.*;

public class Screen extends JPanel {
    public Screen() {
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
