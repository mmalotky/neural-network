package dev10.NeuralNetwork.gui;


import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class NetworkMenu extends JPanel {
    public NetworkMenu(NetworkController networkController) {
        setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new Label("Networks", Label.CENTER));


        List<String> networks = networkController.getSavedNetworkIds().getBody();
        if(networks == null || networks.size() == 0) {
            add(new Label("No Saved Networks", Label.CENTER));
        }
        else {
            for(String network : networks) {
                add(new JRadioButton(network));
            }
        }

        Button newNetworkButton = new Button("New Network");
        newNetworkButton.addActionListener(this::newNetwork);
        add(newNetworkButton);
    }

    private void newNetwork(ActionEvent actionEvent) {
        NetworkTab.layout.show(this.getParent(), "form");
    }

}
