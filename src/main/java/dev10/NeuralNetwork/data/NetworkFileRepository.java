package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.NetworkMapper;
import dev10.NeuralNetwork.models.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class NetworkFileRepository extends FileRepository<Network> {
    public NetworkFileRepository(@Autowired @Qualifier("networkPathFormat") String networkPathFormat) throws DataAccessException {
        super(networkPathFormat, new NetworkMapper());
    }

}
