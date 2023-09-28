package dev10.NeuralNetwork.data.mappers;

import dev10.NeuralNetwork.data.FileData;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;

import java.util.HashMap;
import java.util.List;

public class MapMapper implements Mapper<Map> {
    @Override
    public FileData objectToData(Map map) {
        FileData data = new FileData();
        data.addLine("id="+map.getMapId());
        for(String key : map.getMap().keySet()) {
            MapElement el = map.getMap().get(key);
            data.addLine(key + ";" + el.name());
        }
        return data;
    }

    @Override
    public Map dataToObject(FileData data) {
        List<String> lines = data.getLines();
        String id = lines.get(0).replaceAll("id=", "");

        HashMap<String, MapElement> map = new HashMap<>();
        for(int i = 1; i < lines.size(); i++) {
            String[] set = lines.get(i).split(";");
            MapElement el = MapElement.valueOf(set[1]);
            map.put(set[0], el);
        }

        return new Map(map, id);
    }
}
