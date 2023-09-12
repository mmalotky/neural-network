package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;

public class NetworkTab extends JPanel {
    private final CardLayout layout = new CardLayout();
    private final NetworkMenu menuScreen;
    private final NewNetworkForm formScreen;
    private final EditNetworkForm editScreen;
    public static final String MENU = "1";
    public static final String FORM = "2";
    public static final String EDIT = "3";

    public NetworkTab(NetworkController controller) {
        setLayout(layout);
        menuScreen = new NetworkMenu(controller, this);
        add(menuScreen, MENU);
        formScreen = new NewNetworkForm(controller, this);
        add(formScreen, FORM);
        editScreen = new EditNetworkForm(controller, this);
        add(editScreen, EDIT);
        layout.show(this, MENU);
    }

    public void navigate (String destination) {
        layout.show(this, destination);
        switch (destination) {
            case MENU -> menuScreen.updateUI();
            case FORM -> formScreen.updateUI();
            case EDIT -> editScreen.updateUI();
        }
        this.revalidate();
        this.updateUI();
    }

    public void refresh() {
        menuScreen.refresh();
        editScreen.refresh();
    }
}
