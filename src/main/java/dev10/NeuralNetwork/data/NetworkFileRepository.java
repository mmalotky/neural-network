package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.NetworkMapper;
import dev10.NeuralNetwork.models.Network;
import org.springframework.stereotype.Repository;


@Repository
public class NetworkFileRepository extends FileRepository<Network> {
    public NetworkFileRepository(String networkPathFormat) {
        super.pathFormat = networkPathFormat;
        super.mapper = new NetworkMapper();
    }

}
