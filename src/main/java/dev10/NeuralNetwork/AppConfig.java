package dev10.NeuralNetwork;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public String getNetworkPathFormat() {
        return "./saves/networks/%s.txt";
    }
    @Bean
    public String getMapPathFormat() {
        return "./saves/maps/%s.txt";
    }

}
