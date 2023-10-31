package dev10.NeuralNetwork.gui;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class ErrorGraph extends JPanel {
    private final int MARGIN = 10;
    private final List<Double> data;
    private final double YMAX;

    public ErrorGraph(List<Double> data) {
        this.data = data;
        YMAX = data.stream()
                .max((a, b) -> BigDecimal.valueOf(a - b).setScale(0, RoundingMode.UP).intValue())
                .orElse(1.0);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawLine(MARGIN, getHeight() - MARGIN, MARGIN, MARGIN);
        graphics.drawLine(MARGIN, getHeight() - MARGIN, getWidth() - MARGIN, getHeight() - MARGIN);

        for (int i = 0; i < 20; i ++) {
            int yTick = getHeight() - (((i + 1) * (getHeight() - MARGIN * 2)) / 20 + MARGIN);
            graphics.drawLine(MARGIN, yTick, MARGIN - 10, yTick);

            int xTick = getWidth() - (((i + 1) * (getWidth() - MARGIN * 2)) / 20 + MARGIN);
            graphics.drawLine(xTick, getHeight() - MARGIN, xTick, getHeight() - MARGIN + 10);
        }

        for(int i = 0; i < data.size(); i++) {
            int x = getWidth() - (((20 - i) * (getWidth() - MARGIN * 2)) / 20 + MARGIN);
            int y = (int) (getHeight() - Math.round((getHeight() - MARGIN * 2) * (data.get(i)/YMAX)) - MARGIN);
            graphics.fillOval(x - 3, y - 3, 6, 6);

            if(i < data.size() - 1) {
                int x2 = getWidth() - (((20 - i - 1) * (getWidth() - MARGIN * 2)) / 20 + MARGIN);
                int y2 = (int) (getHeight() - Math.round((getHeight() - MARGIN * 2) * (data.get(i + 1)/YMAX)) - MARGIN);
                graphics.drawLine(x, y, x2, y2);
            }
        }
    }
}
