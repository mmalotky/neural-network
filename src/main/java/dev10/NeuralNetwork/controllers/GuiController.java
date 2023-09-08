package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.gui.*;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

@Controller
public class GuiController extends JFrame {
    public static final CardLayout layout = new CardLayout();
    public static final JPanel panel = new JPanel(layout);

    public GuiController(NetworkController networkController) {
        panel.add(new MainMenu(), GuiNavigation.MAIN_MENU);
        panel.add(new MapMenu(), GuiNavigation.MAP_MENU);
        panel.add(new RunScreen(), GuiNavigation.RUN_SCREEN);
        panel.add(new NetworkMenu(networkController), GuiNavigation.NETWORK_MENU);
        panel.add(new NewNetworkForm(networkController), GuiNavigation.NEW_NETWORK_FORM);
        layout.show(panel, GuiNavigation.MAIN_MENU);

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
