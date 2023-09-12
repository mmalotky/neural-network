package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditNetworkForm extends JPanel {
    private final NetworkController controller;
    private final NetworkMenu menu;

    private static final JTextField idField = new JTextField();
    private static final JSpinner lrField = new JSpinner(new SpinnerNumberModel(0,0,1,0.01));

    public EditNetworkForm(NetworkController controller, NetworkMenu menu) {
        this.controller = controller;
        this.menu = menu;

        setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
    public static void refresh(String id, double lr) {
        idField.setText(id);
        lrField.setValue(lr);
    }

    private void exit(ActionEvent actionEvent) {
        NetworkTab.layout.show(this.getParent(), "menu");
    }

    private void save(ActionEvent actionEvent) {
        String id = idField.getText();
        double lr = (double) lrField.getValue();
        controller.setNetworkId(id);
        controller.setLearningRate(lr);
        controller.saveNetwork();

        menu.refresh();
        exit(actionEvent);
    }
}
