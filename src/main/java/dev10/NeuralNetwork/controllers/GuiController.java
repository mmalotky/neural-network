package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.gui.MainMenu;
import dev10.NeuralNetwork.gui.MapMenu;
import dev10.NeuralNetwork.gui.NetworkMenu;
import dev10.NeuralNetwork.gui.RunScreen;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

@Controller
public class GuiController extends JFrame {
    public static final CardLayout layout = new CardLayout();
    public static final JPanel panel = new JPanel(layout);

    public GuiController() {
        panel.add(new NetworkMenu(), "Networks");
        panel.add(new MainMenu(), "Main Menu");
        panel.add(new MapMenu(), "Maps");
        panel.add(new RunScreen(), "Run");
        layout.show(panel, "Main Menu");

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
