/**
 * Copyright (c) 2013,
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Klasa implementuje fukcje fitness jako pierwiastek z bledu sredniokwadratowego
 */
public class RMSEFitnessCalculator implements AbstractFitnessCalculator{

    @Autowired
    private AbstractForecastCalculator forecastCalculator;

    /**
     * Metoda oblicza wartosc funkcji fitness dla danego chromosomu jako Root Mean Squared
     * Error, czyli pierwiastek z bledu sredniokwadratowego.
     *
     * @param timeSeries Szereg czasowy
     * @param window Okno czasowe
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
