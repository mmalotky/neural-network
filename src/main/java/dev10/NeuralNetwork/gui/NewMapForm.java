package dev10.NeuralNetwork.gui;

import dev10.NeuralNetwork.controllers.MapController;
import dev10.NeuralNetwork.models.MapElement;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * Form for creating new maps
 */
public class NewMapForm extends Form {
    private final MapController controller;
    private final JSpinner heightField = new JSpinner(new SpinnerNumberModel(1,1,100,1));
    private final JSpinner widthField = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    private final JPanel mapField = new JPanel();
    public NewMapForm(MapController controller, MapTab tab) {
        super(tab);
        this.controller = controller;

        add(new Title("New Map"));

        JPanel fields = new JPanel();
        heightField.addChangeListener(this::changeRows);
        fields.add(new Field("Height", heightField));
        widthField.addChangeListener(this::changeColumns);
        fields.add(new Field("Width", widthField));
        add(fields);

        JPanel row = new JPanel();
        row.add(new MapSegment());
        mapField.setLayout(new GridLayout(100,1,0,0));
        mapField.add(row);
        add(new JScrollPane(mapField));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::save);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(saveButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this::exit);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(exitButton);
    }

    /**
     * Adjusts the number of rows in the map field
     * @param changeEvent ChangeEvent trigger
     */
    private void changeRows(ChangeEvent changeEvent) {
        int value = (int) heightField.getValue();
        int rows = mapField.getComponentCount();
        while(rows != value) {
            if(rows > value) {
                mapField.remove(rows - 1);
                --rows;
            } else {
                int cols = (int) widthField.getValue();
                JPanel row = new JPanel();
                for(int i = 0; i < cols; i++) {
                    row.add(new MapSegment());
                }
                mapField.add(row);
                ++rows;
            }
        }
        mapField.updateUI();
    }

    /**
     * Adjusts the number of columns in the map field
     * @param changeEvent ChangeEvent Trigger
     */
    private void changeColumns(ChangeEvent changeEvent) {
        int value = (int) widthField.getValue();
        for(Component component : mapField.getComponents()) {
            JPanel row = (JPanel) component;
            int cols = row.getComponentCount();

            while(cols != value) {
                if(cols > value) {
                    row.remove(cols - 1);
                    --cols;
                } else {
                    row.add(new MapSegment());
                    ++cols;
                }
            }
        }
        mapField.updateUI();
    }
    @Override
    public void save(ActionEvent actionEvent) {
        HashMap<String, MapElement> result = new HashMap<>();
        for(int i = 0; i < mapField.getComponentCount(); i++) {
            JPanel row = (JPanel) mapField.getComponents()[i];
            for(int j = 0; j < row.getComponentCount(); j++) {
                MapSegment segment = (MapSegment) row.getComponents()[j];
                String key = String.format("%s,%s", i, j);
                MapElement el = segment.getElement();
                result.put(key, el);
            }
        }
        controller.newMap(result);
        tab.navigate(MapTab.MENU);
        tab.refresh();
    }

    @Override
    boolean refresh() {
        return true;
    }
}
