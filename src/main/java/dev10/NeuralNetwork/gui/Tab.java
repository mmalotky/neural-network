package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JPanel presets for Tab format
 */
public abstract class Tab extends JPanel {
    public final CardLayout layout = new CardLayout();

    public static final String MENU = "1";
    public static final String FORM = "2";
    public static final String EDIT = "3";
    List<Screen> screens = new ArrayList<>();

    public Tab() {
        setLayout(layout);
    }

    /**
     * Navigate between Screens added to the card layout
     * @param destination String key for destination
     */
    public void navigate (String destination) {
        layout.show(this, destination);
    }

    /**
     * Retrieve data and update the UI
     */
    public void refresh() {
        screens.forEach(Screen::refresh);
    }
}
