package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.models.Network;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class NetworkFileRepository implements NetworkRepository {
    private final String path;
    private final String delimiter = ",";

    public NetworkFileRepository(String path) {
        this.path = path;
    }

    @Override
    public boolean saveNetwork(Network network) {

        return false;
    }

    @Override
    public Network loadNetwork(String id) throws DataAccessException {
        Network network;
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            //TODO:create network
        } catch (Exception e) {
            throw new DataAccessException("Error accessing file at: " + path, e);
        }
        return null;
    }

    private void wrightToFile() throws DataAccessException {
        try(FileWriter writer = new FileWriter(path)) {
            //TODO:wright network
        } catch (IOException e) {
            throw new DataAccessException("Error accessing file at: " + path, e);
        }
    }
}
