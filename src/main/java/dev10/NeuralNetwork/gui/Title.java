package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Presets for JLabel in title format
 */
public class Title extends JLabel {
    public Title(String text) {
        setText(text);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setFont(new Font(Font.SERIF, Font.BOLD, 24));
    }
}
