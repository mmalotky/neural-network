package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditNetworkForm extends Screen {
    private final NetworkController controller;
    private final NetworkTab tab;
    private final JTextField idField = new JTextField();
    private final JSpinner lrField = new JSpinner(new SpinnerNumberModel(0,0,1,0.01));

    public EditNetworkForm(NetworkController controller, NetworkTab tab) {
        this.controller = controller;
        this.tab = tab;

        add(new Label("Edit Network", Label.CENTER));

        add(new Label("ID"));
        add(idField);
        add(new Label("Learning Rate"));
        add(lrField);

        Button saveButton = new Button("Save");
        saveButton.addActionListener(this::save);
        add(saveButton);

        Button exitButton = new Button("Exit");
        exitButton.addActionListener(this::exit);
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
