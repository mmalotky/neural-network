package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditNetworkForum extends Screen {
    private final NetworkController controller;
    private final NetworkTab tab;
    private final JTextField idField = new JTextField();
    private final JSpinner lrField = new JSpinner(new SpinnerNumberModel(0,0,1,0.01));

    public EditNetworkForum(NetworkController controller, NetworkTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new Title("Edit Network"));

        add(new Field("ID", idField));

        add(new Field("Learning Rate", lrField));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::save);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exit);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(exitButton);
    }
    public void refresh() {
        idField.setText(controller.getNetworkId());
        lrField.setValue(controller.getLearningRate());
    }

    private void exit(ActionEvent actionEvent) {
        tab.navigate(NetworkTab.MENU);
    }

    private void save(ActionEvent actionEvent) {
        String id = idField.getText();
        double lr = (double) lrField.getValue();
        controller.setNetworkId(id);
        controller.setLearningRate(lr);
        controller.saveNetwork();

        tab.refresh();
        exit(actionEvent);
    }
}
