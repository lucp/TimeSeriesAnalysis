package forecasting.config;

import forecasting.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StochasticUniversalSamplingConfig {

    @Bean
    public AbstractGeneticAlgorithmOperation selection() {
        return new StochasticUniversalSamplingSelection();
    }

    @Bean
    public AbstractGeneticAlgorithmOperation crossover(){
        return new ArithmeticalCrossover();
    }

    @Bean
    public AbstractGeneticAlgorithmOperation mutation(){
        return new GaussianPerturbation();
    }

    @Bean
    public AbstractForecast forecast(){
        return new TimeSeriesForecast();
    }

    @Bean
    public AbstractFitnessCalculator fitnessCalculator(){
        return new RMSEFitnessCalculator();
    }

    @Bean
    public AbstractForecastCalculator forecastCalculator(){
        return new LinearCombinationForecastCalculator();
    }
}
