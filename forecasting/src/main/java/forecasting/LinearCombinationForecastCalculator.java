package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

public class LinearCombinationForecastCalculator implements AbstractForecastCalculator {

    /**
     * Metoda oblicza przyblizona wartosc w danym punkcie czasu jako kombinacje liniowa
     * genow chromosomu i poprzednich wartosci pobranych korzystajac z STW.
     *
     * @param chromosome Chromosom dla ktorego ma zostac obliczona przyblizona wartosc
     * @param currentIndex Indeks punktu czasu dla ktorego jest obliczane przyblizenie
     * @return Przyblizona wartosc lub null w przypadku gdy nie mozna dopasowac STW
     */
    public Double calculateForecast(TimeSeries timeSeries,
                                    SlidingTimeWindow window,
                                    Chromosome chromosome,
                                    int currentIndex){

        double returnValue = chromosome.getGene(0);

        for(int i = 0; i < window.getLength(); i++){

            int pastIndex = currentIndex - window.getValueAt(i);

            if(pastIndex < 0){
                return null;
            }
            returnValue += chromosome.getGene(i + 1) *
                    timeSeries.getValue(pastIndex).doubleValue();
        }

        return returnValue;
    }
}
