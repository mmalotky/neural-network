package dev10.NeuralNetwork.gui;


import javax.swing.*;
import java.awt.*;

/**
 * List of icons used to paint Map Segments and the run screen
 */
public class MapIcon {
    static Icon WALL = new Icon() {
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

    static Icon FLOOR = new Icon() {
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

    static Icon START = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.BLUE);
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

    static Icon END = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.RED);
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

    static Icon POSITION = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.YELLOW);
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
}
