package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.gui.MainMenu;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

@Controller
public class GuiController {
    JFrame frame = new JFrame("Neural Network");
    MainMenu mainMenu = new MainMenu();

    public GuiController() {
        frame.add(mainMenu.getPanel(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
