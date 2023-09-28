package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.MapMapper;
import dev10.NeuralNetwork.models.Map;
import org.springframework.stereotype.Repository;


@Repository
public class MapFileRepository extends FileRepository<Map> {

    public MapFileRepository(String mapPathFormat) throws DataAccessException {
        super(mapPathFormat, new MapMapper());
    }
}
