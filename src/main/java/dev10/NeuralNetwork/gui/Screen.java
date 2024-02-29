package dev10.NeuralNetwork.gui;

import javax.swing.*;

/**
 * Presets for JPanel in screen format
 */
public abstract class Screen extends JPanel {

    public Screen() {
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Refreshed internal data and updates layouts
     * @return Boolean value representing success of data retrievals
     */
    abstract boolean refresh();
}
