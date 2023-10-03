package dev10.NeuralNetwork.domain;

import dev10.NeuralNetwork.data.DataAccessException;
import dev10.NeuralNetwork.data.FileRepository;
import dev10.NeuralNetwork.models.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkService extends AppService<Network> {

    public NetworkService(@Autowired FileRepository<Network> repository) {
        super(repository);
    }
    @Override
    public Result<Void> save(Network network) {
        Result<Void> result = new Result<>();

        if(network == null) {
            result.addError("Network cannot be null");
            return result;
        }

        checkId(network.getNetworkId(), result);
        int[] layers = new int[network.getLayers().size()];
        for(int i = 0; i < network.getLayers().size(); i++) {
            layers[i] = network.getLayers().get(i).size();
        }

        checkNetworkComponents(network.getOptions().size(), layers, result);
        if(!result.isSuccess()) {
            return result;
        }

        try {
            repository.save(network, network.getNetworkId());
        } catch (DataAccessException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    public Result<Network> newNetwork(int options, int[] layers) {
        Result<Network> result = new Result<>();

        checkNetworkComponents(options, layers, result);
        if(!result.isSuccess()) {
            return result;
        }

        Network network = new Network(options, layers);
        result.setPayload(network);
        return result;
    }

    private void checkNetworkComponents(int options, int[] layers, Result<?> result) {
        if(options < 1) {
            result.addError("There must be at least one option.");
        }
        if(layers == null || layers.length == 0) {
            result.addError("There must be at least one layer");
        }
        else {
            for(int layer : layers) {
                if(layer < 1) {
                    result.addError("There must be at least one neuron in a layer");
                    break;
                }
            }
        }
    }
    public Result<Void> rename(String name, Network network) {
        Result<Void> result = new Result<>();
        checkId(name, result);
        network.setNetworkId(name);
        return result;
    }
}
