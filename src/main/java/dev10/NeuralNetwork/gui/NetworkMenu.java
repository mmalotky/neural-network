package dev10.NeuralNetwork.gui;


import dev10.NeuralNetwork.controllers.NetworkController;
import dev10.NeuralNetwork.domain.NetworkService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NetworkMenu extends JPanel implements GuiNavigation {
    public NetworkMenu(NetworkController networkController) {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new Label("Networks", Label.CENTER));


        List<String> networks = networkController.getSavedNetworkIds().getBody();
        if(networks == null || networks.size() == 0) {
            this.add(new Label("No Saved Networks", Label.CENTER));
        }
        else {
            for(String network : networks) {
                this.add(new JRadioButton(network));
            }
        }

        Button newNetworkButton = new Button("New Network");
        newNetworkButton.addActionListener(this::navigate);
        this.add(newNetworkButton);

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.addActionListener(this::navigate);
        this.add(mainMenuButton);
    }

}
