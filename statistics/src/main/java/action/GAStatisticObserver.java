package action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;

/**
 * Klasa implementuje obserwatora algorytmu genetycznego w celu pozyskania szeregu czasowego
 * wraz z predykcją po zakończeniu działania algorytmu
 */
public class GAStatisticObserver implements GAObserver {

	private TimeSeries forecast;
	private int numOfDataPoints;
    
    public GAStatisticObserver(TimeSeries forecast, int numOfDataPoints){
    	this.forecast = forecast;
        this.numOfDataPoints = numOfDataPoints;
    }
    
    /**
     * Metoda nie posiada implementacji
     *
     * @param fitness Wartosc funkcji fitness najlepszego osobnika w tej iteracji
     * @param best Geny najlepszego osobnika w tej iteracji
     * @param i Numer iteracji
     */
    @Override
    public void update(double fitness, double[] best, int i) {
    }
    
    /**
     * Metoda wywolywana po zakonczeniu dzialania algorytmu genetycznego, przekazuje szereg czasowy zawierajacy
     * predykcje lub nie, w zaleznosci od inicjalizacji/wywolania metody. Nastepnie oddziela dane wejscowe 
     * od danych pochodzacych z predykcji.
     *
     * @param timeSeries Szereg czasowy
     */
    @Override
    public void done(TimeSeries timeSeriesWithForecast){
    	int number = timeSeriesWithForecast.getItemCount() - 1;
		try {
			forecast = timeSeriesWithForecast.createCopy(number - numOfDataPoints, number);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Metoda nie posiada implementacji
     *
     * @param best Chromosom najlepszego osobnika jako tablica doubli
     */
    @Override
    public void done(double[] best) {
    }
}
