package dev10.NeuralNetwork.gui;


import dev10.NeuralNetwork.controllers.NetworkController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Menu for selecting and managing active networks
 */
public class NetworkMenu extends Menu {
    private final NetworkController controller;
    public NetworkMenu(NetworkController controller, NetworkTab tab) {
        super(tab);
        this.controller = controller;

        add(new Title("Networks"));

        refresh();

        JButton deleteButton = new JButton("Delete Network");
        deleteButton.addActionListener(this::delete);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteButton);

        JButton loadButton = new JButton("Load Network");
        loadButton.addActionListener(this::load);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(loadButton);

        JButton editButton = new JButton("Edit Network");
        editButton.addActionListener(this::edit);
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(editButton);

        JButton newNetworkButton = new JButton("New Network");
        newNetworkButton.addActionListener(this::create);
        newNetworkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(newNetworkButton);
    }

    @Override
    public boolean refresh() {
        Arrays.stream(optionsPanel.getComponents())
                .filter(b -> b instanceof AbstractButton)
                .forEach(b -> buttonGroup.remove((AbstractButton) b));
        optionsPanel.removeAll();

        ResponseEntity<List<String>> networksResponse = controller.getSavedNetworkIds();
        List<String> data = networksResponse.getBody();
        boolean success = networksResponse.getStatusCode() == HttpStatus.OK;

        if(data == null || data.size() == 0) {
            JLabel message = new JLabel("No Saved Networks");
            message.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            optionsPanel.add(message);
        }
        else if(!success) {
            String errors = String.join("\n", data);
            JLabel message = new JLabel("Error collecting data: \n" + errors);
            message.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            optionsPanel.add(message);
        }
        else {
            for(String network : data) {
                String status = (network.equals(controller.getNetworkId())) ? "Active" : "";
                JRadioButton button = new JRadioButton(network + "     " + status);
                button.setActionCommand(network);
                optionsPanel.add(button);
                buttonGroup.add(button);
            }
        }
        optionsPanel.updateUI();
        return success;
    }

    @Override
    void delete(ActionEvent actionEvent) {
        String selected = getSelection();
        controller.delete(selected);
        refresh();
    }

    @Override
    void load(ActionEvent actionEvent) {
        String selected = getSelection();
        controller.loadNetwork(selected);
        tab.refresh();
    }
}
