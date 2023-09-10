package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class MapTab extends JPanel {
    public MapTab() {
        setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new Label("Maps", Label.CENTER));
    }
}
