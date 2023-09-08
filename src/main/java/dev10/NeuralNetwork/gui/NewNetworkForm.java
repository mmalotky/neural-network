package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.GuiController;
import dev10.NeuralNetwork.controllers.NetworkController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class NewNetworkForm extends JPanel implements GuiNavigation {

    private int options = 1;
    private List<Integer> layers = new ArrayList<>();

    public NewNetworkForm(NetworkController networkController) {
        this.setBorder(BorderFactory.createEmptyBorder(500,300,100,300));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new Label(NEW_NETWORK_FORM, Label.CENTER));

        this.add(new Label("Options"));


        this.add(new Label("Layers"));


        Button saveButton = new Button("Save");
        saveButton.addActionListener(this::save);
        this.add(saveButton);

        Button networkMenuButton = new Button(NETWORK_MENU);
        networkMenuButton.addActionListener(this::navigate);
        this.add(networkMenuButton);

        GuiController.panel.repaint();
    }

    private void save(ActionEvent e) {
        Map<String, int[]> data = new HashMap<>();
        data.put("Options", new int[]{options});
        data.put("Layers", layers.stream().mapToInt(Integer::intValue).toArray());

        //networkController.newNetwork(data);
        this.navigate(new ActionEvent(e.getSource(), e.getID(), MAIN_MENU));
    }


}
