package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.NetworkMapper;
import dev10.NeuralNetwork.models.Network;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class NetworkFileRepository implements NetworkRepository {
    private final String pathFormat;

    private final NetworkMapper mapper = new NetworkMapper();

    public NetworkFileRepository(String pathFormat) {
        this.pathFormat = pathFormat;
    }

    @Override
    public List<String> getSavedNetworkIds() throws DataAccessException {
        List<String> saveList = new ArrayList<>();

        String filePath = pathFormat.substring(0, 7);
        File saves = new File(filePath);
        if(saves.exists()) {
            if(saves.listFiles() == null) {
                return saveList;
            }
            saveList = Arrays.stream(saves.listFiles()).map(File::getName).toList();
        }
        else {
            throw new DataAccessException("File not found at: " + filePath, new FileNotFoundException());
        }
        return saveList;
    }

    @Override
    public void saveNetwork(Network network) throws DataAccessException {
        String filePath = String.format(pathFormat, network.getNetworkId());
        NetworkData data = mapper.networkToData(network);
        try(PrintWriter writer = new PrintWriter(filePath)) {
            for(String line : data.getLines()) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            File file = new File(filePath);
            try {
                if(!file.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException ex) {
                throw new DataAccessException("Error creating new file at: " + filePath, ex);
            }
            saveNetwork(network);
        }
    }

    @Override
    public Network loadNetwork(String id) throws DataAccessException {
        String filePath = String.format(pathFormat, id);
        Network network;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            NetworkData data = new NetworkData();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                data.addLine(line);
            }
            network = mapper.dataToNetwork(data);
        } catch (Exception e) {
            throw new DataAccessException("Error accessing file at: " + filePath, e);
        }
        return network;
    }
}
