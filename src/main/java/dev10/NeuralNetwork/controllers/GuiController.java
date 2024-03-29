package dev10.NeuralNetwork.controllers;
import dev10.NeuralNetwork.gui.*;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Initializes GUI and sets up visual components
 */
@Controller
public class GuiController extends JFrame {

    public GuiController(NetworkController networkController, MapController mapController) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Run", new RunScreen(networkController, mapController));
        tabs.addTab("Networks", new NetworkTab(networkController));
        tabs.addTab("Maps", new MapTab(mapController));

        tabs.setPreferredSize(new Dimension(500,500));
        add(tabs);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
