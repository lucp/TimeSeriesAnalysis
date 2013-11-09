package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

/**
 * Klasa implemetuje obliczanie predykcji na podstawie ARMA
 */
public class ARMAForecastCalculator implements AbstractForecastCalculator {

    /**
     * Metoda oblicza przyblizona wartosc w danym punkcie czasu jako sumę :
     * <ul>
     *  <li>kombinacji liniowej pierwszej połowy genow chromosomu i poprzednich wartosci pobranych korzystajac z STW oraz</li>
     *  <li>kombinacji liniowej drugiej połowy genów chromosomu i błędów predykcji poprzednich wartości </li>
     * </ul>
     * @param timeSeries Szereg czasowy dla którego wykonywane jest przybliżenie
     * @param window Okno czasowe predykcji
     * @param chromosome Chromosom dla ktorego ma zostac obliczona predykcja
     * @param currentIndex Indeks punktu czasu dla ktorego jest obliczane predykcja
     * @return Predykcja lub null w przypadku gdy nie mozna dopasowac STW
     */
    @Override
    public Double calculateForecast(TimeSeries timeSeries,
                                    SlidingTimeWindow window,
                                    Chromosome chromosome,
                                    int currentIndex) {

        double returnValue = chromosome.getGene(0);

        for(int i = 0; i < window.getLength(); i++){

            int pastIndex = currentIndex - window.getValueAt(i);

            if(pastIndex < 0){
                return null;
            }

            Double pastForecast = new LinearCombinationForecastCalculator().calculateForecast(timeSeries,
                    window,
                    chromosome,
                    pastIndex);

            returnValue += chromosome.getGene(i + 1) *
                    timeSeries.getValue(pastIndex).doubleValue();

            if(pastForecast != null){
                returnValue += chromosome.getGene(i + 1 + window.getLength()) *
                    (timeSeries.getValue(pastIndex).doubleValue() - pastForecast);
            }
        }

        return returnValue;
    }
}
