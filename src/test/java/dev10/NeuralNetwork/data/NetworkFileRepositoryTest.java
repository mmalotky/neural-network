package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class NetworkFileRepositoryTest {
    String pathFormat = "./testData/%s.txt";
    NetworkFileRepository repository = new NetworkFileRepository(pathFormat);

    private final Network test1 = new Network(1, new int[]{ 1 });
    private final Network test2 = new Network(2, new int[]{ 2, 2 });

    private final Network test3 = new Network(3, new int[]{ 5, 4 });

    public NetworkFileRepositoryTest() {
        test3.setNetworkId("test");
    }

    @Test
    void shouldSaveToNewFile() throws DataAccessException {
        repository.saveNetwork(test1);

        File file = new File(String.format(pathFormat, test1.getNetworkId()));

        assertTrue(file.exists());
        assertTrue(file.delete());
    }

    @Test
    void shouldSaveMultipleFiles() throws DataAccessException {
        repository.saveNetwork(test1);
        repository.saveNetwork(test2);

        File file1 = new File(String.format(pathFormat, test1.getNetworkId()));
        File file2 = new File(String.format(pathFormat, test2.getNetworkId()));

        assertTrue(file1.exists());
        assertTrue(file2.exists());

        assertTrue(file1.delete());
        assertTrue(file2.delete());
    }

    @Test
    void shouldOverwriteExistingFile() throws DataAccessException {
        repository.saveNetwork(test3);

        File file = new File(String.format(pathFormat, test3.getNetworkId()));
        assertTrue(file.exists());
    }
}