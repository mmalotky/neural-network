package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Forum Component for editing existing networks
 */
public class EditNetworkForm extends Form {
    private final NetworkController controller;
    private final JTextField idField = new JTextField();
    private final JSpinner lrField = new JSpinner(new SpinnerNumberModel(0,0,1,0.01));

    public EditNetworkForm(NetworkController controller, NetworkTab tab) {
        super(tab);
        this.controller = controller;

        add(new Title("Edit Network"));

        JPanel fields = new JPanel();
        fields.add(new Field("Network ID", idField));
        fields.add(new Field("Learning Rate", lrField));
        add(fields);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::save);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exit);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(exitButton);
    }

    @Override
    public boolean refresh() {
        String id = controller.getNetworkId();
        double lr = controller.getLearningRate();
        idField.setText(id);
        lrField.setValue(lr);
        return id != null && lr != 0;
    }

    @Override
    public void save(ActionEvent actionEvent) {
        String id = idField.getText();
        double lr = (double) lrField.getValue();
        controller.setNetworkId(id);
        controller.setLearningRate(lr);
        controller.saveNetwork();

        tab.refresh();
        exit(actionEvent);
    }
}
