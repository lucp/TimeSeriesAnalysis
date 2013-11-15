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

package forecasting.config;

/**
 * Singleton zawierajacy ustawienia algorytmu genetycznego, pozwala na wybranie metody selekcji i
 * sposobu obliczania predykcji oraz zdefiniowanie czy maja zostac uzyte wielowatkowe wersje operacji
 * algorytmu genetycznego.
 */
public class GASettings {

    private static GASettings instance = new GASettings();

    private SelectionMethod selectionMethod = SelectionMethod.ROULETTE_WHEEL_SELECTION;
    private ForecastMethod forecastMethod = ForecastMethod.LINEAR_COMBINATION_FORECAST;

    private boolean concurrent = false;

    private GASettings(){
    }

    /**
     * Pobierz instancje singletona
     *
     * @return Singleton
     */
    public static GASettings getInstance(){
        return instance;
    }

    /**
     * Pobierz aktualnie ustawiona metode obliczania predykcji
     *
     * @return Metoda obliczania predykcji
     */
    public ForecastMethod getForecastMethod() {
        return forecastMethod;
    }

    /**
     * Ustaw metode obliczania predykcji
     *
     * @param forecastMethod Metoda obliczania predykcji
     */
    public void setForecastMethod(ForecastMethod forecastMethod) {
        this.forecastMethod = forecastMethod;
    }

    /**
     * Pobierz aktualnie ustawiona metode selekcji
     *
     * @return Metoda selekcji
     */
    public SelectionMethod getSelectionMethod() {
        return selectionMethod;
    }

    /**
     * Ustaw metode selekcji
     *
     * @param selectionMethod Metoda oselekcji
     */
    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    /**
     * Pobierz ustawienie wielowatkowosci
     *
     * @return Flaga wielowatkowosci
     */
    public boolean isConcurrent() {
        return concurrent;
    }

    /**
     * Ustaw wielowatkowosc
     *
     * @param concurrent Flaga wielowatkowosci
     */
    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

}
