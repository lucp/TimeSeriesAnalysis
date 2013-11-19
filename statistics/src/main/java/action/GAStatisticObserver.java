/**
 * Copyright (c) 2013, Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika
 * Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

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
