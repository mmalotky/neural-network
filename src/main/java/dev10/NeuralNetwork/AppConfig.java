package dev10.NeuralNetwork;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean Configuration for data paths
 */
@Configuration
public class AppConfig {
    @Bean(name = "networkPathFormat")
    public String getNetworkPathFormat() {
        return "./saves/networks/%s.txt";
    }
    @Bean(name = "mapPathFormat")
    public String getMapPathFormat() {
        return "./saves/maps/%s.txt";
    }

}
