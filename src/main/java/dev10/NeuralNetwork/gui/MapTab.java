package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;

/**
 * Tab for selecting and managing maps
 */
public class MapTab extends Tab {

    public MapTab(MapController controller) {
        MapMenu menuScreen = new MapMenu(controller, this);
        add(menuScreen, MENU);
        screens.add((menuScreen));

        NewMapForm formScreen = new NewMapForm(controller, this);
        add(formScreen, FORM);
        screens.add(formScreen);

        layout.show(this, MENU);
    }
}
