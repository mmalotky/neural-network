package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.FileRepository;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapService extends AppService<Map> {
    public MapService(@Autowired FileRepository<Map> repository) {
        super(repository);
    }

    @Override
    Result<Void> save(Map map) {
        Result<Void> result = new Result<>();
        if(map == null || map.getMap() == null || map.getMapId() == null) {
            result.addError("Null pointer");
            return result;
        }

        checkId(map.getMapId(), result);
        checkMap(map, result);
        if(!result.isSuccess()) return result;

        try {
            repository.save(map, map.getMapId());
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    private void checkMap(Map map, Result<?> result) {
        if(!map.getMap().containsValue(MapElement.START)) result.addError("Map missing start");
        if(!map.getMap().containsValue(MapElement.END)) result.addError("Map missing end");
        if(map.getMap().keySet().stream().anyMatch(k -> map.keyToCoordinates(k) == null)) {
            result.addError("Map contains invalid coordinates");
        }
        if(!result.isSuccess()) return;
        if(!findPath(map)) result.addError("No paths found");
    }

    private boolean findPath(Map map) {
        int[] start = map.getStart();
        return checkPath(map, new ArrayList<>(), start);
    }

    private boolean checkPath(Map map, List<String> checked, int[] position) {
        String right = map.coordinatesToKey(new int[]{position[0] + 1, position[1]});
        if(checkDirection(map, checked, right)) return true;

        String left = map.coordinatesToKey(new int[]{position[0] - 1, position[1]});
        if(checkDirection(map, checked, left)) return true;

        String up = map.coordinatesToKey(new int[]{position[0], position[1] + 1});
        if(checkDirection(map, checked, up)) return true;

        String down = map.coordinatesToKey(new int[]{position[0], position[1] - 1});
        return checkDirection(map, checked, down);
    }

    private boolean checkDirection(Map map, List<String> checked, String direction) {
        if(!checked.contains(direction)) {
            MapElement el = map.getMap().get(direction);
            if(el == null) return false;
            switch (el) {
                case END -> {
                    return true;
                }
                case FLOOR -> {
                    checked.add(direction);
                    return checkPath(map, checked, map.keyToCoordinates(direction));
                }
            }
        }
        return false;
    }
}
