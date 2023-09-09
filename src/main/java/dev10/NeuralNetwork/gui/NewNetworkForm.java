package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class NewNetworkForm extends JPanel implements GuiNavigation {

    private int options = 1;
    private List<Integer> layers = new ArrayList<>();

    public NewNetworkForm() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new Label("New Network", Label.CENTER));

        this.add(new Label("Options"));
        JSpinner optionsSpinner = new JSpinner(new SpinnerNumberModel(1,1,null,1));
        this.add(optionsSpinner);

        this.add(new Label("Layers"));
        JSpinner layersSpinner = new JSpinner(new SpinnerNumberModel(1,1,null,1));
        this.add(layersSpinner);

        Button saveButton = new Button("Save");
        saveButton.addActionListener(this::save);
        this.add(saveButton);

        Button mainMenuButton = new Button("Exit");
        mainMenuButton.setActionCommand(NETWORK_MENU);
        mainMenuButton.addActionListener(this::navigate);
        this.add(mainMenuButton);
    }

    private void save(ActionEvent e) {
        Map<String, int[]> data = new HashMap<>();
        data.put("Options", new int[]{options});
        data.put("Layers", layers.stream().mapToInt(Integer::intValue).toArray());

        //networkController.newNetwork(data);
        this.navigate(new ActionEvent(e.getSource(), e.getID(), NETWORK_MENU));
    }


}
