package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;

public class NetworkTab extends JPanel {
    private final CardLayout layout = new CardLayout();
    private final NetworkMenu menuScreen;
    private final NewNetworkForum forumScreen;
    private final EditNetworkForum editScreen;
    public static final String MENU = "1";
    public static final String FORUM = "2";
    public static final String EDIT = "3";

    public NetworkTab(NetworkController controller) {
        setLayout(layout);
        menuScreen = new NetworkMenu(controller, this);
        add(menuScreen, MENU);
        forumScreen = new NewNetworkForum(controller, this);
        add(forumScreen, FORUM);
        editScreen = new EditNetworkForum(controller, this);
        add(editScreen, EDIT);
        layout.show(this, MENU);
    }

    public void navigate (String destination) {
        layout.show(this, destination);
    }

    public void refresh() {
        menuScreen.refresh();
        editScreen.refresh();
    }
}
