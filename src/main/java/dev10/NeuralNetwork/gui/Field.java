package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Presets for JPanel in data field components
 */
public class Field extends JPanel {
    public Field(String text, Component component) {
        JLabel label = new JLabel(text);
        label.setLabelFor(component);
        component.setPreferredSize(new Dimension(100,20));
        add(label);
        add(component);
    }
}
