package forecasting.config;

import forecasting.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Klasa bedaca konfiguracja Springa (Spring JavaConfig)
 */
@Configuration
public class ForecastConfig {

    @Bean
    public AbstractGeneticAlgorithmOperation selection() {
        if(GASettings.getInstance().getSelectionMethod() == SelectionMethod.ROULETTE_WHEEL_SELECTION){
            return new RouletteWheelSelection();
        }
        if(GASettings.getInstance().getSelectionMethod() == SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION){
            return new StochasticUniversalSamplingSelection();
        }
        return null;
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
        if(GASettings.getInstance().getForecastMethod() == ForecastMethod.LINEAR_COMBINATION_FORECAST){
            return new LinearCombinationForecastCalculator();
        }
        if(GASettings.getInstance().getForecastMethod() == ForecastMethod.ARMA_FORECAST){
            return new ARMAForecastCalculator();
        }
        return null;
    }
}
