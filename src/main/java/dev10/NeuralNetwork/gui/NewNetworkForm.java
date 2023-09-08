package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class NewNetworkForm extends JPanel implements GuiNavigation {
    public NewNetworkForm() {
        this.setBorder(BorderFactory.createEmptyBorder(300,300,100,300));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new Label(NEW_NETWORK_FORM, Label.CENTER));

        Button networkMenuButton = new Button(NETWORK_MENU);
        networkMenuButton.addActionListener(this::navigate);
        this.add(networkMenuButton);
    }
}
