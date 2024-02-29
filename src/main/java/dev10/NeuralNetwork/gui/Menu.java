package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Presets for Menu Screen format
 */
public abstract class Menu extends Screen {
    public final JPanel optionsPanel = new JPanel();
    public final ButtonGroup buttonGroup = new ButtonGroup();
    public final Tab tab;

    public Menu(Tab tab) {
        this.tab = tab;
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(optionsPanel));
    }

    /**
     * Loads the selected and refreshes the page
     * @param actionEvent ActionEvent trigger
     */
    abstract void load(ActionEvent actionEvent);

    /**
     * Navigates to a form for creating a new item
     * @param actionEvent ActionEvent trigger
     */
    public void create(ActionEvent actionEvent) {
        tab.navigate(Tab.FORM);
    };

    /**
     * Navigates to a form for editing a list item
     * @param actionEvent ActionEvent trigger
     */
    public void edit(ActionEvent actionEvent) {
        load(actionEvent);
        tab.navigate(Tab.EDIT);
    }

    /**
     * Removes selected list item
     * @param actionEvent ActionEvent trigger
     */
    abstract void delete(ActionEvent actionEvent);

    /**
     * Retrieves the selected value in the menu
     * @return String id of the selected map
     */
    public String getSelection() {
        ButtonModel selection = buttonGroup.getSelection();
        return selection != null ? selection.getActionCommand() : null;
    }
}
