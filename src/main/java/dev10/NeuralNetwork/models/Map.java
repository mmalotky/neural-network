package dev10.NeuralNetwork.models;

import java.util.*;

/**
 * Navigable map for training neural networks composed of MapElements and their coordinates
 */
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

    /**
     * Get the Map's identifier
     * @return String id
     */
    public String getMapId() {
        return mapId;
    }

    /**
     * Set the Map's identifier
     * @param mapId String id to be set
     */
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    /**
     * Returns a HashMap that maps coordinate Strings to MapElements
     * @return HashMap map
     */
    public HashMap<String, MapElement> getMap() {
        return map;
    }

    /**
     * Gets the coordinates of the starting position on the map
     * @return int[] containing coordinates [x,y]
     */
    public int[] getStart() {
        String start = map.keySet().stream()
                .filter(k -> map.get(k) == MapElement.START)
                .findFirst().orElse(null);
        return keyToCoordinates(start);
    }

    /**
     * Gets the coordinates of the ending position on the map
     * @return int[] containing coordinates [x,y]
     */
    public int[] getEnd() {
        String end = map.keySet().stream()
                .filter(k -> map.get(k) == MapElement.END)
                .findFirst().orElse(null);
        return keyToCoordinates(end);
    }

    /**
     * Converts map keys to coordinate sets
     * @param key String key for the map
     * @return int[] containing coordinates [x,y]
     */
    public int[] keyToCoordinates(String key) {
        if(key == null || !key.matches("^[0-9]+,[0-9]+$")) return null;
        return Arrays.stream(key.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Converts coordinate sets to map keys
     * @param coordinates int[] containing coordinates [x,y]
     * @return String key for the map
     */
    public String coordinatesToKey(int[] coordinates) {
        if(coordinates == null || coordinates.length != 2) return null;
        return String.format("%s,%s", coordinates[0], coordinates[1]);
    }

    /**
     * Get MapElement by its key
     * @param key String key for the map
     * @return MapElement for the position
     */
    public MapElement getCoordinatesElement(String key) {
        return map.get(key);
    }

    /**
     * Get MapElement by its coordinate pair
     * @param coordinates int[] containing coordinates [x,y]
     * @return MapElement for the position
     */
    public MapElement getCoordinatesElement(int[] coordinates) {
        String key = coordinatesToKey(coordinates);
        return map.get(key);
    }

    /**
     * Get the width dimension of the map
     * @return int number of elements from left to right
     */
    public int getWidth() {
        return map.keySet().stream()
                .mapToInt(key -> keyToCoordinates(key)[0])
                .max().orElse(0);
    }

    /**
     * Get the height dimension of the map
     * @return int number of elements from top to bottom
     */
    public int getHeight() {
        return map.keySet().stream()
                .mapToInt(key -> keyToCoordinates(key)[1])
                .max().orElse(0);
    }
}
