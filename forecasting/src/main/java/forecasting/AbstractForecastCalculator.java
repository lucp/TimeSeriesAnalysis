package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

/**
 * Interfejs obliczania predykcji
 */
public interface AbstractForecastCalculator {

    /**
     * Oblicz predykcje
     *
     * @param timeSeries Szereg czasowy
     * @param window Okno czasowe
     * @param chromosome Chromosom bedacy rozwiazaniem na podstawie ktorego obliczana jest predykcja
     * @param currentIndex Indeks
     * @return Predykcja jako Double
     */
    public Double calculateForecast(TimeSeries timeSeries,
                                    SlidingTimeWindow window,
                                    Chromosome chromosome,
                                    int currentIndex);
}
