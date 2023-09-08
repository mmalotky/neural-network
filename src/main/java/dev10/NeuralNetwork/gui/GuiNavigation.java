package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.GuiController;

import java.awt.event.ActionEvent;

public interface GuiNavigation {
    String MAIN_MENU = "1";
    String NETWORK_MENU = "2";
    String MAP_MENU = "3";
    String RUN_SCREEN = "4";
    String NEW_NETWORK_FORM = "5";

    default void navigate(ActionEvent event) {
        GuiController.layout.show(GuiController.panel, event.getActionCommand());
    }
}
