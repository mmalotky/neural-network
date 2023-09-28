package dev10.NeuralNetwork.data.mappers;

import dev10.NeuralNetwork.data.FileData;
import dev10.NeuralNetwork.models.Map;
import dev10.NeuralNetwork.models.MapElement;

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
        return null;
    }
}
