package dev10.NeuralNetwork.gui;

import java.awt.event.ActionEvent;

/**
 * Presets for form screen format
 */
public abstract class Form extends Screen {

    public final Tab tab;
    public Form(Tab tab) {
        this.tab = tab;
    }

    /**
     * Navigates to the Tab Menu
     * @param actionEvent ActionEvent trigger
     */
    public void exit(ActionEvent actionEvent) {
        tab.navigate(Tab.MENU);
    }



    /**
     * Applies the settings specified in the fields, saves, and exits the forum
     * @param actionEvent ActionEvent trigger
     */
    abstract void save(ActionEvent actionEvent);
}
