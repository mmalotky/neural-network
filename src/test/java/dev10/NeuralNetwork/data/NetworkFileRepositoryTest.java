package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NetworkFileRepositoryTest {
    String pathFormat = "./testData/networks/%s.txt";
    NetworkFileRepository repository = new NetworkFileRepository(pathFormat);

    private final Network test1 = new Network(1, new int[]{ 1 });
    private final Network test2 = new Network(2, new int[]{ 2, 2 });

    private final Network test3 = new Network(3, new int[]{ 5, 4 });

    public NetworkFileRepositoryTest() throws DataAccessException {
        test3.setNetworkId("test");
    }

    @Test
    void shouldSaveToNewFile() throws DataAccessException {
        repository.save(test1, test1.getNetworkId());

        File file = new File(String.format(pathFormat, test1.getNetworkId()));

        assertTrue(file.exists());
        assertTrue(file.delete());
    }

    @Test
    void shouldSaveMultipleFiles() throws DataAccessException {
        repository.save(test1, test1.getNetworkId());
        repository.save(test2, test2.getNetworkId());

        File file1 = new File(String.format(pathFormat, test1.getNetworkId()));
        File file2 = new File(String.format(pathFormat, test2.getNetworkId()));

        assertTrue(file1.exists());
        assertTrue(file2.exists());

        assertTrue(file1.delete());
        assertTrue(file2.delete());
    }

    @Test
    void shouldOverwriteExistingFile() throws DataAccessException {
        repository.save(test3, test3.getNetworkId());

        File file = new File(String.format(pathFormat, test3.getNetworkId()));
        assertTrue(file.exists());

        double expected = test3.getLayers().get(0).get(0).getConnections().get(0).getWeight();
        try(BufferedReader reader = new BufferedReader(new FileReader(String.format(pathFormat, "test")))) {
            reader.readLine();
            reader.readLine();
            double actual = Double.parseDouble(reader.readLine().replaceAll(",.+", "").substring(7));

            assertEquals(expected,actual);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldLoadANetwork() throws DataAccessException {
        Network network = repository.load("test");
        assertNotNull(network);

        repository.save(test1, test1.getNetworkId());
        Network load = repository.load(test1.getNetworkId());
        double expected = test1.getLayers().get(0).get(0).getConnections().get(0).getWeight();
        double actual = load.getLayers().get(0).get(0).getConnections().get(0).getWeight();

        assertEquals(expected, actual);

        File file1 = new File(String.format(pathFormat, test1.getNetworkId()));
        if(!file1.delete()) {
            fail("Failed to delete at path: " + String.format(pathFormat, test1.getNetworkId()));
        }
    }

    @Test
    void shouldNotLoadAMissingNetwork() {
        try {
            repository.load("");
            fail();
        } catch(DataAccessException e) {
            assertEquals(e.getMessage(), "Error accessing file at: ./testData/networks/.txt");
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
        File file = new File("./testData/networks/deleteTest.txt");
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