package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.MapMapper;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;

import java.util.HashMap;
import java.util.List;

public class MapRepositoryDouble extends FileRepository<Map> {
    private final Map test1 = new Map(new HashMap<>(), "test1");

    private final Map test2 = new Map(new HashMap<>(), "test2");

    private final Map test3 = new Map(new HashMap<>(), "test3");

    public MapRepositoryDouble() throws DataAccessException {
        super("./testData/null", new MapMapper());

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

    @Override
    public List<String> getSavedIds() {
        return List.of("test1", "test2", "test3");
    }

    @Override
    public void save(Map network, String id) {
    }

    @Override
    public Map load(String id) throws DataAccessException {
        return switch (id) {
            case "test1" -> test1;
            case "test2" -> test2;
            case "test3" -> test3;
            default -> throw new DataAccessException("Error accessing file at: {filePath}");
        };
    }

    @Override
    public boolean delete(String id) {
        return true;
    }
}
