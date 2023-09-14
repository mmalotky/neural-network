package dev10.NeuralNetwork.gui;


import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class NetworkMenu extends Screen {
    private final JPanel networksPanel = new JPanel();
    private final ButtonGroup networksGroup = new ButtonGroup();
    private final NetworkController controller;
    private final NetworkTab tab;
    public NetworkMenu(NetworkController controller, NetworkTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new JLabel("Networks", JLabel.CENTER));

        networksPanel.setLayout(new BoxLayout(networksPanel, BoxLayout.Y_AXIS));
        refresh();
        add(new JScrollPane(networksPanel));

        JButton deleteButton = new JButton("Delete Network");
        deleteButton.addActionListener(this::deleteNetwork);
        add(deleteButton);

        JButton loadButton = new JButton("Load Network");
        loadButton.addActionListener(this::loadNetwork);
        add(loadButton);

        JButton editButton = new JButton("Edit Network");
        editButton.addActionListener(this::editNetwork);
        add(editButton);

        JButton newNetworkButton = new JButton("New Network");
        newNetworkButton.addActionListener(this::newNetwork);
        add(newNetworkButton);
    }
    public void refresh() {
        Arrays.stream(networksPanel.getComponents())
                .filter(b -> b instanceof AbstractButton)
                .forEach(b -> networksGroup.remove((AbstractButton) b));
        networksPanel.removeAll();

        List<String> networks = controller.getSavedNetworkIds().getBody();
        if(networks == null || networks.size() == 0) {
            networksPanel.add(new Label("No Saved Networks", Label.CENTER));
        }
        else {
            for(String network : networks) {
                String status = (network.equals(controller.getNetworkId())) ? "Active" : "";
                JRadioButton button = new JRadioButton(network + "     " + status);
                button.setActionCommand(network);
                networksPanel.add(button);
                networksGroup.add(button);
            }
        }
        networksPanel.updateUI();
    }

    private void newNetwork(ActionEvent actionEvent) {
        tab.navigate(NetworkTab.FORUM);
    }

    private void editNetwork(ActionEvent actionEvent) {
        loadNetwork(actionEvent);
        tab.navigate(NetworkTab.EDIT);
    }

    private void deleteNetwork(ActionEvent actionEvent) {
        String selected = getSelection();
        controller.delete(selected);
        refresh();
    }

    private void loadNetwork(ActionEvent actionEvent) {
        String selected = getSelection();
        controller.loadNetwork(selected);
        refresh();
    }

    private String getSelection() {
        ButtonModel selection = networksGroup.getSelection();
        return selection != null ? selection.getActionCommand() : null;
    }
}
