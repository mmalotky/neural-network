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

    /**
     * Validates and creates a new Network object
     * @param options Number of options for the output layer
     * @param layers An array indicating the number and size of input and hidden layers
     * @return Result object containing the new Network
     */
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

    /**
     * Validate the components of a Network object
     * @param options Number of options for the output layer
     * @param layers An array indicating the number and size of input and hidden layers
     * @param result Result object for storing error messages
     */
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

    /**
     * Validates and checks a new id input for a Network object
     * @param name The id to be checked
     * @param network The nNetwork to be renamed
     * @return Result object indicating success or failure
     */
    public Result<Void> rename(String name, Network network) {
        Result<Void> result = new Result<>();
        checkId(name, result);
        network.setNetworkId(name);
        return result;
    }
}
