package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;

import javax.swing.*;
import java.awt.*;

public class MapTab extends JPanel {
    private final CardLayout layout = new CardLayout();
    private final MapMenu menuScreen;
    private final NewMapForum forumScreen;
    public static final String MENU = "1";
    public static final String FORUM = "2";

    public MapTab(MapController controller) {
        setLayout(layout);
        menuScreen = new MapMenu(controller, this);
        add(menuScreen, MENU);
        forumScreen = new NewMapForum(controller, this);
        add(forumScreen, FORUM);
        layout.show(this, MENU);
    }

    public void navigate (String destination) {
        layout.show(this, destination);
    }

    public void refresh() {
        menuScreen.refresh();
    }
}
