package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapFileRepositoryTest {
    String pathFormat = "./testData/maps/%s.txt";
    MapFileRepository repository = new MapFileRepository(pathFormat);
    private final Map test1 = new Map(new HashMap<>());

    private final Map test2 = new Map(new HashMap<>());

    private final Map test3 = new Map(new HashMap<>(), "test");

    MapFileRepositoryTest() throws DataAccessException {
        HashMap<String, MapElement> map1 = test1.getMap();
        map1.put("0,0", MapElement.START);
        map1.put("0,1", MapElement.FLOOR);
        map1.put("1,0", MapElement.WALL);
        map1.put("1,1", MapElement.END);

        HashMap<String, MapElement> map2 = test2.getMap();
        map2.put("0,0", MapElement.START);
        map2.put("0,1", MapElement.WALL);
        map2.put("1,0", MapElement.FLOOR);
        map2.put("1,1", MapElement.END);

        HashMap<String, MapElement> map3 = test3.getMap();
        map3.put("0,0", MapElement.START);
        map3.put("0,1", MapElement.FLOOR);
        map3.put("0,2", MapElement.WALL);
        map3.put("1,0", MapElement.WALL);
        map3.put("1,1", MapElement.FLOOR);
        map3.put("1,2", MapElement.END);
    }


    @Test
    void shouldSaveToNewFile() throws DataAccessException {
        repository.save(test1, test1.getMapId());

        File file = new File(String.format(pathFormat, test1.getMapId()));

        assertTrue(file.exists());
        assertTrue(file.delete());
    }

    @Test
    void shouldSaveMultipleFiles() throws DataAccessException {
        repository.save(test1, test1.getMapId());
        repository.save(test2, test2.getMapId());

        File file1 = new File(String.format(pathFormat, test1.getMapId()));
        File file2 = new File(String.format(pathFormat, test2.getMapId()));

        assertTrue(file1.exists());
        assertTrue(file2.exists());

        assertTrue(file1.delete());
        assertTrue(file2.delete());
    }

    @Test
    void shouldOverwriteExistingFile() throws DataAccessException {
        repository.save(test3, test3.getMapId());

        File file = new File(String.format(pathFormat, test3.getMapId()));
        assertTrue(file.exists());

        try(BufferedReader reader = new BufferedReader(new FileReader(String.format(pathFormat, "test")))) {
            reader.readLine();
            reader.readLine();
            String actual = reader.readLine();

            assertEquals("0,1;FLOOR",actual);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldLoadAMap() throws DataAccessException {
        Map map = repository.load("test");
        assertNotNull(map);

        repository.save(test1, test1.getMapId());
        MapElement load = repository.load(test1.getMapId()).getMap().get("0,1");
        MapElement actual = test1.getMap().get("0,1");

        assertEquals(load, actual);

        File file1 = new File(String.format(pathFormat, test1.getMapId()));
        if(!file1.delete()) {
            fail("Failed to delete at path: " + String.format(pathFormat, test1.getMapId()));
        }
    }

    @Test
    void shouldNotLoadAMissingMap() {
        try {
            repository.load("");
            fail();
        } catch(DataAccessException e) {
            assertEquals(e.getMessage(), "Error accessing file at: ./testData/maps/.txt");
        }
    }

    @Test
    void shouldRetrieveSaveNames() throws DataAccessException {
        List<String> networkIds = repository.getSavedIds();
        assertTrue(networkIds.contains("test"));
        assertEquals(networkIds.size(), 1);
    }

    @Test
    void shouldDeleteSave() throws IOException {
        File file = new File("./testData/maps/deleteTest.txt");
        assertTrue(file.exists() || file.createNewFile());

        assertTrue(repository.delete("deleteTest"));
        assertFalse(file.exists());
    }

    @Test
    void shouldNotDeleteMissing() throws DataAccessException {
        assertFalse(repository.delete("NaN"));
        assertTrue(repository.getSavedIds().size() >= 1);
    }
}