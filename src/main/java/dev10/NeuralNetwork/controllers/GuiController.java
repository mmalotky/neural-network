package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.gui.MainMenu;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

    public static void toNetworkMenu(ActionEvent actionEvent) {
        System.out.println("Networks");
    }

    public static void toMapMenu(ActionEvent actionEvent) {
        System.out.println("Maps");
    }

    public static void toRunScreen(ActionEvent actionEvent) {
        System.out.println("Run");
    }
}
