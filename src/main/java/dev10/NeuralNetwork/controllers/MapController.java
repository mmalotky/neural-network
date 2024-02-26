package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.domain.MapService;
import dev10.NeuralNetwork.domain.Result;
import dev10.NeuralNetwork.models.Direction;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

/**
 * Manage the active map, includes persistent data management as well as navigation controls
 */
@Controller
public class MapController {

    private Map map;
    private int[] coordinates;

    @Autowired
    private final MapService service;

    public MapController(MapService service) {
        this.service = service;
    }

    /**
     * Get a list of saved map ids
     * @return Response entity containing the saved ids on success or error messages on failure
     */
    public ResponseEntity<List<String>> getMapIds() {
        Result<List<String>> result = service.getSavedIds();
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Converts map data into a Map object, sets the new map to active, and saves the map to persistent data
     * @param map Hashmap of data mapping coordinates to MapElements
     * @return Response entity indicating success or failure
     */
    public ResponseEntity<List<String>> newMap(HashMap<String, MapElement> map) {
        this.map = new Map(map);
        coordinates = this.map.getStart();
        return saveMap();
    }

    /**
     * Saves the current active map to persistent data
     * @return Response entity indicating success or failure
     */
    public ResponseEntity<List<String>> saveMap() {
        Result<Void> result = service.save(map);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Sets the active map
     * @param id String id of the map to be activated
     * @return Response entity indicating success or failure
     */
    public ResponseEntity<List<String>> loadMap(String id) {
        Result<Map> result = service.load(id);
        if(result.isSuccess()) {
            map = result.getPayload();
            coordinates = map.getStart();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Remove a map from persistent data
     * @param id String id of the map to be removed
     * @return Response entity indicating success or failure
     */
    public ResponseEntity<List<String>> deleteMap(String id) {
        Result<Void> result = service.delete(id);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get the current active Map's id
     * @return The id string or null if no map is active
     */
    public String getMapID() {
        return map == null ? null : map.getMapId();
    }

    /**
     * Get the current location on the active map
     * @return an array containing the coordinates on the map
     */
    public int[] getCoordinates() {
        return coordinates;
    }

    /**
     * Attempts to navigate on the map given a direction. If the space is open, the location is updated to the new
     * coordinates. If the space is blocked, the coordinates remain the same. If the space is the ending space, the
     * coordinates are reset to the start.
     * @param direction The direction toward which to navigate
     */
    public void navigate(Direction direction) {
        int[] location = {coordinates[0] + direction.value[0], coordinates[1] + direction.value[1]};
        MapElement el =  getCoordinatesElement(location);
        if(el == MapElement.END) coordinates = map.getStart();
        else if(el != null && el != MapElement.WALL) coordinates = location;
    }

    /**
     * Gets the map element on the map given a set of coordinates
     * @param coordinates The coordinates to be checked
     * @return The MapElement at the specified coordinates
     */
    public MapElement getCoordinatesElement(int[] coordinates) {
        return map.getCoordinatesElement(coordinates);
    }

    /**
     * Gets the ending coordinates on the active map
     * @return An int array containing the ending coordinates
     */
    public int[] getEnd() { return map.getEnd(); }

    /**
     * Gets the dimensions of the current active map
     * @return An int array of [Width, Height]
     */
    public int[] getDimensions() {
        return new int[] {map.getWidth(), map.getHeight()};
    }
}
