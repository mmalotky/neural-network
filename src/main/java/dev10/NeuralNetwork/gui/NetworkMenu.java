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

        add(new Title("Networks"));

        networksPanel.setLayout(new BoxLayout(networksPanel, BoxLayout.Y_AXIS));
        refresh();
        add(new JScrollPane(networksPanel));

        JButton deleteButton = new JButton("Delete Network");
        deleteButton.addActionListener(this::deleteNetwork);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        JButton loadButton = new JButton("Load Network");
        loadButton.addActionListener(this::loadNetwork);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(loadButton);

        JButton editButton = new JButton("Edit Network");
        editButton.addActionListener(this::editNetwork);
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(editButton);

        JButton newNetworkButton = new JButton("New Network");
        newNetworkButton.addActionListener(this::newNetwork);
        newNetworkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newNetworkButton);
    }
    public void refresh() {
        Arrays.stream(networksPanel.getComponents())
                .filter(b -> b instanceof AbstractButton)
                .forEach(b -> networksGroup.remove((AbstractButton) b));
        networksPanel.removeAll();

        List<String> networks = controller.getSavedNetworkIds().getBody();
        if(networks == null || networks.size() == 0) {
            JLabel message = new JLabel("No Saved Networks");
            message.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            networksPanel.add(message);
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
        tab.refresh();
    }

    private String getSelection() {
        ButtonModel selection = networksGroup.getSelection();
        return selection != null ? selection.getActionCommand() : null;
    }
}
