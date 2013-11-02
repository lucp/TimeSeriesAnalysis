package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

import javax.swing.*;

public abstract class AbstractForecast extends SwingWorker<Chromosome, Double[]> {

    /**
     * Inicjalizuj predykcje, wywolanie tej motody oznacza ze klasa po zakonczeniu algorytmu genetycznego
     * uruchomi predykcje i szereg czasowy w metodzie done bedzie zawieral predykcje.
     *
     * @param numOfDataPoints Liczba punktow czasowych dla ktorych ma zostac wykonana predykcja
     */
    public abstract void initializeForecast(int numOfDataPoints);

    /**
     * Inicjalizuj algorytm genetyczny
     *
     * @param timeSeries Szereg czasowy
     * @param populationSize Rozmiar populacji
     * @param slidingTimeWindow
     * @param numOfIterations
     * @param crossoverProbability
     * @param mutationProbability
     * @param percentOfKeptFromSelection
     * @param percentOfKeptFromCrossover
     * @param percentOfKeptFromMutation
     */
    public abstract void initializeGeneticAlgorithm(TimeSeries timeSeries,
                                                    int populationSize,
                                                    SlidingTimeWindow slidingTimeWindow,
                                                    int numOfIterations,
                                                    double crossoverProbability,
                                                    double mutationProbability,
                                                    double percentOfKeptFromSelection,
                                                    double percentOfKeptFromCrossover,
                                                    double percentOfKeptFromMutation);

    public abstract void addObserver(GAObserver o);

    /**
     * Oblicz predykcje
     *
     * @param timeSeries Szereg czasowy dla ktorego ma zostac wykonana predykcja
     * @param numOfDataPoints Liczba punktow czasowych dla ktorych ma zostac wykonana predykcja
     * @param genes Rozwiazanie ktore ma byc uzyte w predykcji
     * @return Szereg czasowy powiekszony o predykcje
     */
    public abstract TimeSeries doForecast(TimeSeries timeSeries, int numOfDataPoints, double[] genes);
}
