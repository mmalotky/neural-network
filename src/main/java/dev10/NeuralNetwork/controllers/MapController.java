package dev10.NeuralNetwork.controllers;

import dev10.NeuralNetwork.domain.MapService;
import dev10.NeuralNetwork.domain.Result;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;


@Controller
public class MapController {

    private Map map;
    private int[] coordinates;

    @Autowired
    private final MapService service;

    public MapController(MapService service) {
        this.service = service;
    }

    public ResponseEntity<List<String>> getMapIds() {
        Result<List<String>> result = service.getSavedIds();
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<String>> newMap(HashMap<String, MapElement> map) {
        this.map = new Map(map);
        coordinates = this.map.getStart();
        return saveMap();
    }

    public ResponseEntity<List<String>> saveMap() {
        Result<Void> result = service.save(map);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    public ResponseEntity<List<String>> deleteMap(String id) {
        Result<Void> result = service.delete(id);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(result.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    public String getMapID() {
        return map == null ? null : map.getMapId();
    }

    public int[] getCoordinates() {
        return coordinates;
    }
    public void navigate(Direction direction) {
        int[] location = {coordinates[0] + direction.value[0], coordinates[1] + direction.value[1]};
        MapElement el =  getCoordinatesElement(location);
        if(el == MapElement.END) coordinates = map.getStart();
        else if(el != null && el != MapElement.WALL) coordinates = location;
    }

    public MapElement getCoordinatesElement(int[] coordinates) {
        return map.getCoordinatesElement(coordinates);
    }

    public int[] getEnd() { return map.getEnd(); }

    public int[] getDimensions() {
        return new int[] {map.getWidth(), map.getHeight()};
    }
}
