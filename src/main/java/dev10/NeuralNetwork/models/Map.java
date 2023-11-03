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

    public int[] getEnd() {
        String end = map.keySet().stream()
                .filter(k -> map.get(k) == MapElement.END)
                .findFirst().orElse(null);
        return keyToCoordinates(end);
    }

    public int[] keyToCoordinates(String key) {
        if(key == null || !key.matches("^[0-9]+,[0-9]+$")) return null;
        return Arrays.stream(key.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public String coordinatesToKey(int[] coordinates) {
        if(coordinates == null || coordinates.length != 2) return null;
        return String.format("%s,%s", coordinates[0], coordinates[1]);
    }

    public MapElement getCoordinatesElement(String key) {
        return map.get(key);
    }

    public MapElement getCoordinatesElement(int[] coordinates) {
        String key = coordinatesToKey(coordinates);
        return map.get(key);
    }

    public int getWidth() {
        return map.keySet().stream()
                .mapToInt(key -> keyToCoordinates(key)[0])
                .max().orElse(0);
    }

    public int getHeight() {
        return map.keySet().stream()
                .mapToInt(key -> keyToCoordinates(key)[1])
                .max().orElse(0);
    }
}
