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
        GASettings settings = GASettings.getInstance();
        if(settings.getSelectionMethod() == SelectionMethod.ROULETTE_WHEEL_SELECTION){
            if(settings.isConcurrent()){
                return new ConcurrentRouletteWheelSelection();
            }else{
                return new RouletteWheelSelection();
            }
        }
        if(settings.getSelectionMethod() == SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION){
            if(settings.isConcurrent()){
                return new ConcurrentStochasticUniversalSamplingSelection();
            }else{
                return new StochasticUniversalSamplingSelection();
            }
        }
        return null;
    }

    @Bean
    public AbstractGeneticAlgorithmOperation crossover(){
        GASettings settings = GASettings.getInstance();
        if(settings.isConcurrent()){
            return new ConcurrentArithmeticalCrossover();
        }else{
            return new ArithmeticalCrossover();
        }
    }

    @Bean
    public AbstractGeneticAlgorithmOperation mutation(){
        GASettings settings = GASettings.getInstance();
        if(settings.isConcurrent()){
            return new ConcurrentGaussianPerturbation();
        }else{
            return new GaussianPerturbation();
        }
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
