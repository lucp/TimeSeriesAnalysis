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

import javax.swing.*;

/**
 * Interfejs predykcji szeregu czasowego.
 */
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
     * @param slidingTimeWindow Okno czasowe
     * @param numOfIterations Liczba iteracji
     * @param crossoverProbability Prawdopodobienstwo krzyzowania
     * @param mutationProbability Prawdopodobienstwo mutacji
     * @param percentOfKeptFromSelection Procent osobnikow pozostawianych po selekcji
     * @param percentOfKeptFromCrossover Procent osobnikow pozostawianych po krzyzowaniu
     * @param percentOfKeptFromMutation Procent osobnikow pozostawianych po mutacji
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

    /**
     * Dodaj obserwatora
     *
     * @param o Obserwator
     */
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
