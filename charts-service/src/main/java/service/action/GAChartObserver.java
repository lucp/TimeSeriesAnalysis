/**
 * Copyright (c) 2013
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package service.action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa implementuje obserwatora algorytmu genetycznego w celu nanoszenia zmian na wykresach
 */
public class GAChartObserver implements GAObserver {

    private FitnessChart fitChart;
    private TimeSeriesChart tmChart;
    private int numOfDataPoints;
    
    public GAChartObserver(FitnessChart fitChart, TimeSeriesChart tmChart, int numOfDataPoints){
        this.fitChart = fitChart;
        this.tmChart = tmChart;
        this.numOfDataPoints = numOfDataPoints;
    }
    
    /**
     * Algorytm co iterację "pushuje" te dane do observerow. Nastepnie dodawany jest element 
     * do zestawu danych, po czym odswiezany i przerysowywany jest panel z wykresem.
     *
     * @param fitness Wartosc funkcji fitness najlepszego osobnika w tej iteracji
     * @param best Geny najlepszego osobnika w tej iteracji
     * @param i Numer iteracji
     */
    @Override
    public void update(double fitness, double[] best, int i) {

    	fitChart.setVisible(true);

        try{
        	fitChart.addValue(fitness,"Max", i);
        }catch(IllegalArgumentException ex){
            System.err.println("Some minor chart problem: "+ex.getMessage());
        }

        try{
        	fitChart.ripejnt();
        	fitChart.validate();

        }catch(IllegalArgumentException ex){
            System.err.println("Some minor chart problem: "+ex.getMessage());
        }
    }
    
    /**
     * Metoda wywolywana po zakonczeniu dzialania algorytmu genetycznego, przekazuje szereg czasowy zawierajacy
     * predykcje lub nie, w zaleznosci od inicjalizacji/wywolania metody. Nastepnie tworzony jest wykres 
     * na podstawie otrzymanego szeregu czasowego.
     *
     * @param timeSeries Szereg czasowy
     */
    @Override
    public void done(TimeSeries timeSeriesWithForecast){

        List<TimeSeries> timeSeriesWithForecastList = new ArrayList<>();
        timeSeriesWithForecastList.add(timeSeriesWithForecast);
        
        tmChart.createChartPanel(timeSeriesWithForecastList, this.numOfDataPoints);
        tmChart.validate();
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
