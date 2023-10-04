package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.MapRepositoryDouble;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MapServiceTest {
    private final MapRepositoryDouble repository = new MapRepositoryDouble();
    private final MapService service = new MapService(repository);

    private final Map testMap = new Map(new HashMap<>());

    public MapServiceTest() throws DataAccessException {
        HashMap<String, MapElement> mMap1 = testMap.getMap();
        mMap1.put("0,0", MapElement.START);
        mMap1.put("0,1", MapElement.END);
    }

    @Test
    void shouldGetIds() {
        Result<List<String>> result = service.getSavedIds();
        assertTrue(result.isSuccess());
        assertEquals(result.getPayload().get(0), "test1");
        assertEquals(result.getPayload().get(1), "test2");
        assertEquals(result.getPayload().get(2), "test3");
    }

    @Test
    void shouldSave() {
        testMap.setMapId("test");
        Result<Void> result1 = service.save(testMap);
        assertTrue(result1.isSuccess());

        Map map2 = new Map(new HashMap<>());
        HashMap<String, MapElement> mMap2 = map2.getMap();
        mMap2.put("0,0", MapElement.START);
        mMap2.put("1,0", MapElement.FLOOR);
        mMap2.put("2,0", MapElement.FLOOR);
        mMap2.put("3,0", MapElement.FLOOR);
        mMap2.put("4,0", MapElement.FLOOR);
        mMap2.put("0,1", MapElement.FLOOR);
        mMap2.put("1,1", MapElement.WALL);
        mMap2.put("2,1", MapElement.FLOOR);
        mMap2.put("3,1", MapElement.WALL);
        mMap2.put("4,1", MapElement.FLOOR);
        mMap2.put("0,2", MapElement.END);
        mMap2.put("1,2", MapElement.WALL);
        mMap2.put("2,2", MapElement.FLOOR);
        mMap2.put("3,2", MapElement.FLOOR);
        mMap2.put("4,2", MapElement.FLOOR);
        Result<Void> result2 = service.save(map2);
        assertTrue(result2.isSuccess());
    }

    @Test
    void shouldNotSaveNulls() {
        Result<Void> result1 = service.save(null);
        assertFalse(result1.isSuccess());

        Result<Void> result2 = service.save(new Map(null));
        assertFalse(result2.isSuccess());

        testMap.setMapId(null);
        Result<Void> result3 = service.save(testMap);
        assertFalse(result3.isSuccess());
    }

    @Test
    void shouldNotSaveInvalidId() {
        testMap.setMapId("");
        Result<Void> result1 = service.save(testMap);
        assertFalse(result1.isSuccess());

        testMap.setMapId("a/i");
        Result<Void> result2 = service.save(testMap);
        assertFalse(result2.isSuccess());

        testMap.setMapId(".no");
        Result<Void> result3 = service.save(testMap);
        assertFalse(result3.isSuccess());

        testMap.setMapId("#no");
        Result<Void> result4 = service.save(testMap);
        assertFalse(result4.isSuccess());

        testMap.setMapId("\no");
        Result<Void> result5 = service.save(testMap);
        assertFalse(result5.isSuccess());
    }

    @Test
    void shouldNotSaveInvalidMap() {
        Map map = new Map(new HashMap<>());
        HashMap<String, MapElement> mMap = map.getMap();

        Result<Void> result1 = service.save(map);
        assertFalse(result1.isSuccess());

        mMap.put("0,0", MapElement.START);
        Result<Void> result2 = service.save(map);
        assertFalse(result2.isSuccess());

        mMap.replace("0,0", MapElement.END);
        Result<Void> result3 = service.save(map);
        assertFalse(result3.isSuccess());

        mMap.put("0,1", MapElement.START);
        runControl(map);

        mMap.put("", MapElement.FLOOR);
        Result<Void> result4 = service.save(map);
        assertFalse(result4.isSuccess());

        mMap.remove("");
        runControl(map);

        mMap.put("12", MapElement.FLOOR);
        Result<Void> result5 = service.save(map);
        assertFalse(result5.isSuccess());

        mMap.remove("12");
        runControl(map);

        mMap.put("1,2,3", MapElement.FLOOR);
        Result<Void> result6 = service.save(map);
        assertFalse(result6.isSuccess());

        mMap.remove("1,2,3");
        runControl(map);

        mMap.put("1,a", MapElement.FLOOR);
        Result<Void> result7 = service.save(map);
        assertFalse(result7.isSuccess());

        mMap.remove("1,a");
        runControl(map);

        mMap.put("1,\n2", MapElement.FLOOR);
        Result<Void> result8 = service.save(map);
        assertFalse(result8.isSuccess());
    }

    private void runControl(Map map) {
        Result<Void> control = service.save(map);
        assertTrue(control.isSuccess());
    }

    @Test
    void shouldNotSaveMapWithoutAPath() {
        Map map1 = new Map(new HashMap<>());
        HashMap<String, MapElement> mMap1 = map1.getMap();
        mMap1.put("0,0", MapElement.START);
        mMap1.put("0,2", MapElement.END);
        Result<Void> result1 = service.save(map1);
        assertFalse(result1.isSuccess());

        mMap1.put("0,1", MapElement.WALL);
        Result<Void> result2 = service.save(map1);
        assertFalse(result2.isSuccess());
    }

    @Test
    void shouldLoad() {
        Result<Map> result = service.load("test1");
        assertTrue(result.isSuccess());
        Result<Map> result2 = service.load("test2");
        assertTrue(result2.isSuccess());
        Result<Map> result3 = service.load("test3");
        assertTrue(result3.isSuccess());
    }

    @Test
    void shouldNotLoadMissing() {
        Result<Map> result = service.load("missing");
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotLoadNullOrInvalid() {
        Result<Map> result = service.load(null);
        assertFalse(result.isSuccess());

        Result<Map> result2 = service.load("");
        assertFalse(result2.isSuccess());

        Result<Map> result3 = service.load(".no");
        assertFalse(result3.isSuccess());
    }

    @Test
    void shouldDelete() {
        assertTrue(service.delete("test1").isSuccess());
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(service.delete("NaN").isSuccess());
    }
}
