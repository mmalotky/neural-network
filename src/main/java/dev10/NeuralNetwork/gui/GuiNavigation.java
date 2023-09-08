package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.GuiController;

import java.awt.event.ActionEvent;

public interface GuiNavigation {
    String MAIN_MENU = "Main Menu";
    String NETWORK_MENU = "Networks";
    String MAP_MENU = "Maps";
    String RUN_SCREEN = "Run Network";
    String NEW_NETWORK_FORM = "New Network";

    default void navigate(ActionEvent event) {
        GuiController.layout.show(GuiController.panel, event.getActionCommand());
    }
}
