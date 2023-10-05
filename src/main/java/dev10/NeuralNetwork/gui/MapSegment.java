package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.models.MapElement;

import javax.swing.*;
import java.awt.*;

public class MapSegment extends JButton {
    private MapElement el;

    public MapSegment() {
        setMargin(new Insets(0,0,0,0));
        setPreferredSize(new Dimension(10, 10));

        el = MapElement.FLOOR;
        setIcon(MapIcons.FLOOR);
        addActionListener(e -> rotateElements());
    }

    public MapElement getElement() {
        return el;
    }

    private void rotateElements() {
        el = switch(el) {
            case FLOOR -> MapElement.WALL;
            case WALL -> MapElement.START;
            case START -> MapElement.END;
            case END -> MapElement.FLOOR;
        };
        setIcon(switch (el) {
            case FLOOR -> MapIcons.FLOOR;
            case WALL -> MapIcons.WALL;
            case START -> MapIcons.START;
            case END -> MapIcons.END;
        });
    }
}
