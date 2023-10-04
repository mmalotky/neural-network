package dev10.NeuralNetwork.models;

import java.util.*;

public class Map {
    private String mapId;
    private final HashMap<String, MapElement> map;

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

    public int[] getStart() {
        String start = map.keySet().stream()
                .filter(k -> map.get(k) == MapElement.START)
                .findFirst().orElse(null);
        return keyToCoordinates(start);
    }

    public int[] keyToCoordinates(String key) {
        if(key == null || !key.matches("^[0-9]+,[0-9]+$")) return null;
        return Arrays.stream(key.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public String coordinatesToKey(int[] coordinates) {
        if(coordinates == null || coordinates.length != 2) return null;
        return String.format("%s,%s", coordinates[0], coordinates[1]);
    }
}
