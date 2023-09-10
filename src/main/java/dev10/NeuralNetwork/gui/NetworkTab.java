package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;

public class NetworkTab extends JPanel {
    public static CardLayout layout = new CardLayout();

    public NetworkTab(NetworkController controller) {
        setLayout(layout);
        add(new NetworkMenu(controller), "menu");
        add(new NewNetworkForm(), "form");
        layout.show(this, "menu");
    }
}
