package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.NetworkController;

/**
 * Tab for selecting and managing networks
 */
public class NetworkTab extends Tab {

    public NetworkTab(NetworkController controller) {
        NetworkMenu menuScreen = new NetworkMenu(controller, this);
        add(menuScreen, MENU);
        screens.add(menuScreen);

        NewNetworkForm forumScreen = new NewNetworkForm(controller, this);
        add(forumScreen, FORM);
        screens.add(forumScreen);

        EditNetworkForm editScreen = new EditNetworkForm(controller, this);
        add(editScreen, EDIT);
        screens.add(editScreen);

        layout.show(this, MENU);
    }
}
