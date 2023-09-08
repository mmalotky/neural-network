package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.GuiController;

import java.awt.event.ActionEvent;

public interface GuiNavigation {
    public static final String MAIN_MENU = "Main Menu";
    public static final String NETWORK_MENU = "Networks";
    public static final String MAP_MENU = "Maps";
    public static final String RUN_SCREEN = "Run Network";
    public static final String NEW_NETWORK_FORM = "New Network";

    default void navigate(ActionEvent event) {
        GuiController.layout.show(GuiController.panel, event.getActionCommand());
    }
}
