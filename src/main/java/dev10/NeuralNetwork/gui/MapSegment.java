package dev10.NeuralNetwork.gui;

import javax.swing.*;
import java.awt.*;

public class MapSegment extends JButton {
    private boolean isActive = false;
    Icon on = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }

        @Override
        public int getIconWidth() {
            return 10;
        }

        @Override
        public int getIconHeight() {
            return 10;
        }
    };

    Icon off = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, getIconWidth(), getIconHeight());       }

        @Override
        public int getIconWidth() {
            return 10;
        }

        @Override
        public int getIconHeight() {
            return 10;
        }
    };

    public MapSegment() {
        setMargin(new Insets(0,0,0,0));
        setPreferredSize(new Dimension(10, 10));
        setIcon(off);
        addActionListener(e -> setActive(!isActive));
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        setIcon(isActive ? on : off);
    }
}
