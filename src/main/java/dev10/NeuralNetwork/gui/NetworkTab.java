package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;

public class NetworkTab extends JPanel {
    public static CardLayout layout = new CardLayout();

    public NetworkTab(NetworkController controller) {
        setLayout(layout);
        NetworkMenu menu = new NetworkMenu(controller);
        add(menu, "menu");
        add(new NewNetworkForm(controller, menu), "form");
        layout.show(this, "menu");
    }
}
