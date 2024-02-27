package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.MapMapper;
import dev10.NeuralNetwork.models.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Accesses file data for Map objects
 */
@Repository
public class MapFileRepository extends FileRepository<Map> {
    public MapFileRepository(@Autowired @Qualifier("mapPathFormat") String mapPathFormat) throws DataAccessException {
        super(mapPathFormat, new MapMapper());
    }
}
