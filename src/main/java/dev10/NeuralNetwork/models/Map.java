package dev10.NeuralNetwork.models;

import java.util.HashMap;
import java.util.UUID;

public class Map {
    private String mapId;
    private HashMap<String, MapElement> map;

    public Map(HashMap<String, MapElement> map) {
        mapId = UUID.randomUUID().toString();
        this.map = map;
    }

    public Map(HashMap<String, MapElement> map, String id) {
        this.mapId = id;
        this.map = map;
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
