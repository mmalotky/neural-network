package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.NetworkRepositoryDouble;
import dev10.NeuralNetwork.models.Network;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NetworkServiceTest {
    NetworkRepositoryDouble repository = new NetworkRepositoryDouble();
    NetworkService service = new NetworkService(repository);

    @Test
    void shouldGetNetworkIds() throws DataAccessException {
        Result<List<String>> result = service.getSavedNetworkIds();
        assertTrue(result.isSuccess());
        assertEquals(result.getPayload().get(0), "test");
        assertEquals(result.getPayload().get(1), "test2");
    }

    @Test
    void shouldSaveNetwork() {
        Network network = new Network(1, new int[]{1});
        Result<Void> result = service.saveNetwork(network);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotSaveNull() {
        Result<Void> result = service.saveNetwork(null);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotSaveInvalidId() {
        Network network = new Network(1, new int[]{1});
        network.setNetworkId(null);
        Result<Void> result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network.setNetworkId("");
        Result<Void> result2 = service.saveNetwork(network);
        assertFalse(result2.isSuccess());

        network.setNetworkId("a/i");
        Result<Void> result3 = service.saveNetwork(network);
        assertFalse(result3.isSuccess());

        network.setNetworkId(".no");
        Result<Void> result4 = service.saveNetwork(network);
        assertFalse(result4.isSuccess());

        network.setNetworkId("#no");
        Result<Void> result5 = service.saveNetwork(network);
        assertFalse(result5.isSuccess());

        network.setNetworkId("\n");
        Result<Void> result6 = service.saveNetwork(network);
        assertFalse(result6.isSuccess());
    }

    @Test
    void shouldNotSaveInvalidComponents() {
        Network network = new Network(0, new int[]{1});
        Result<Void> result = service.saveNetwork(network);
        assertFalse(result.isSuccess());

        network = new Network(1, new int[]{0});
        Result<Void> result2 = service.saveNetwork(network);
        assertFalse(result2.isSuccess());
    }

    @Test
    void shouldCreateANewNetwork() {
        Result<Network> result = service.newNetwork(1, new int[] {1});
        assertTrue(result.isSuccess());
        assertEquals(1, result.getPayload().getOptions().size());
        assertEquals(1, result.getPayload().getLayers().size());
    }

    @Test
    void shouldNotCreateAnInvalidNetwork() {
        Result<Network> result = service.newNetwork(0, new int[] {1});
        assertFalse(result.isSuccess());

        Result<Network> result2 = service.newNetwork(1, new int[] {0});
        assertFalse(result2.isSuccess());

        Result<Network> result3 = service.newNetwork(0, new int[0]);
        assertFalse(result3.isSuccess());

        Result<Network> result4 = service.newNetwork(0, null);
        assertFalse(result4.isSuccess());

        Result<Network> result5 = service.newNetwork(0, new int[] {-1});
        assertFalse(result5.isSuccess());
    }

    @Test
    void shouldLoadNetwork() {
        Result<Network> result = service.loadNetwork("test");
        assertTrue(result.isSuccess());
        Result<Network> result2 = service.loadNetwork("test2");
        assertTrue(result2.isSuccess());
    }

    @Test
    void shouldNotLoadMissingNetwork() {
        Result<Network> result = service.loadNetwork("missing");
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotLoadNullOrInvalid() {
        Result<Network> result = service.loadNetwork(null);
        assertFalse(result.isSuccess());

        Result<Network> result2 = service.loadNetwork("");
        assertFalse(result2.isSuccess());

        Result<Network> result3 = service.loadNetwork(".no");
        assertFalse(result3.isSuccess());
    }

    @Test
    void shouldRename() {
        Network network = new Network(1, new int[]{1});
        Result<Void> result = service.rename("test", network);
        assertTrue(result.isSuccess());
        assertEquals("test", network.getNetworkId());
    }

    @Test
    void shouldNotRenameInvalid() {
        Network network = new Network(1, new int[]{1});
        Result<Void> result = service.rename("", network);
        assertFalse(result.isSuccess());

        Result<Void> result2 = service.rename(null, network);
        assertFalse(result2.isSuccess());

        Result<Void> result3 = service.rename(".no", network);
        assertFalse(result3.isSuccess());

        Result<Void> result4 = service.rename("   ", network);
        assertFalse(result4.isSuccess());
    }

    @Test
    void shouldDelete() {
        assertTrue(service.delete("test").isSuccess());
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(service.delete("NaN").isSuccess());
    }
}