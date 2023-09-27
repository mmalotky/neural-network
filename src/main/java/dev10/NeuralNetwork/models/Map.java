package dev10.NeuralNetwork.models;

import java.util.HashMap;
import java.util.UUID;

public class Map {
    private String mapId;
    private final HashMap<String, MapElement> map = new HashMap<>();

    public Map() {
        mapId = UUID.randomUUID().toString();
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public HashMap<String, MapElement> getMap() {
        return map;
    }
}
