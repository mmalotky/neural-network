package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.MapMapper;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MapFileRepositoryDouble extends FileRepository<Map> {

    private final Map test1 = new Map(new HashMap<>(), "test1");

    private final Map test2 = new Map(new HashMap<>(), "test2");

    public MapFileRepositoryDouble() throws DataAccessException {
        super("./testData/error", new MapMapper());

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
    }

    @Override
    public List<String> getSavedIds() {
        return List.of("test1", "test2");
    }

    @Override
    public void save(Map map, String id) {
    }

    @Override
    public Map load(String id) throws DataAccessException {
        if(Objects.equals(id, test1.getMapId())) {
            return test1;
        } else if (Objects.equals(id, test2.getMapId())) {
            return test2;
        }
        else {
            throw new DataAccessException("Error accessing file at: {filePath}");
        }
    }

    @Override
    public boolean delete(String id) {
        return true;
    }
}
