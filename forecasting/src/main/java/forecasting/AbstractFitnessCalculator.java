package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

/**
 * Interfejs obliczania wartosci funkcji fitness
 */
public interface AbstractFitnessCalculator {

    /**
     * Oblicz funkcje fitness
     *
     * @param timeSeries Szereg czasowy
     * @param window Okno czasowe
     * @param chromosome Chromosom dla ktorego obliczana jest wartosc funkcji fitness
     * @return Wartosc funkcji fitness jako double
     */
    public double calculateFitness(TimeSeries timeSeries,
                                   SlidingTimeWindow window,
                                   Chromosome chromosome);
}
