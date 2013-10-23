package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;

public class RMSEFitnessCalculator implements AbstractFitnessCalculator{

    @Autowired
    AbstractForecastCalculator forecastCalculator;

    /**
     * Metoda oblicza wartosc funkcji fitness dla danego chromosomu jako Root Mean Squared
     * Error, czyli pierwiastek z bledu sredniokwadratowego.
     *
     * @param chromosome Chromosom dla ktorego ma zostac obliczona wartosc funkcji fitness
     * @return Wartosc funkcji fitness
     */
    public double calculateFitness(TimeSeries timeSeries, SlidingTimeWindow window, Chromosome chromosome){

        /*
        SSE - Sum of Squared Errors, suma kwadratow bledow
         */
        double sse = 0;

        /*
        l - liczba forecastow
         */
        int l = 0;

        for(int i = timeSeries.getItemCount() - 1; i > 0; i--){

            Double forecast = forecastCalculator.calculateForecast(timeSeries,
                    window,
                    chromosome,
                    i);

            if(forecast == null){
                continue;
            }
            l++;

            double realVal = timeSeries.getValue(i).doubleValue();

            double e = realVal - forecast;

            sse += Math.pow(e, 2);
        }

        return Math.sqrt(sse / l);
    }
}
