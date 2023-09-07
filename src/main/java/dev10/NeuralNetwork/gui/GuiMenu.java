package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.GuiController;

import java.awt.event.ActionEvent;

public interface GuiMenu {
    default void navigate(ActionEvent event) {
        GuiController.layout.show(GuiController.panel, event.getActionCommand());
    }
}
