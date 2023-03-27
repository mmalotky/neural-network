package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkFileRepositoryTest {
    String pathFormat = "./testData/%s.txt";
    NetworkFileRepository repository = new NetworkFileRepository(pathFormat);

    private final Network test1 = new Network(1, new int[]{ 1 });
    private final Network test2 = new Network(2, new int[]{ 2, 2 });
    private final Network test3 = new Network(3, new int[]{ 5, 4 });

    @Test
    void shouldSaveToNewFile() throws DataAccessException {
        repository.saveNetwork(test1);
    }
}