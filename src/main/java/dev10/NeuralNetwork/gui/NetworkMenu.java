package dev10.NeuralNetwork.gui;


import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class NetworkMenu extends JPanel {
    private final JPanel networksPanel = new JPanel();
    private final NetworkController controller;
    public NetworkMenu(NetworkController controller) {
        this.controller = controller;

        setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new Label("Networks", Label.CENTER));

        networksPanel.setLayout(new BoxLayout(networksPanel, BoxLayout.Y_AXIS));
        refresh();
        add(new JScrollPane(networksPanel));

        Button newNetworkButton = new Button("New Network");
        newNetworkButton.addActionListener(this::newNetwork);
        add(newNetworkButton);
    }
    public void refresh() {
        networksPanel.removeAll();

        List<String> networks = controller.getSavedNetworkIds().getBody();
        if(networks == null || networks.size() == 0) {
            networksPanel.add(new Label("No Saved Networks", Label.CENTER));
        }
        else {
            for(String network : networks) {
                networksPanel.add(new JRadioButton(network));
            }
        }
    }

    private void newNetwork(ActionEvent actionEvent) {
        NetworkTab.layout.show(this.getParent(), "form");
    }
}
