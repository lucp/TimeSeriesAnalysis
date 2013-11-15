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

import org.jfree.data.time.TimeSeries;

/**
 * Interejs obserwatora algorytmu genetycznego, pozwala na obserwowanie pod katem zdarzen:
 * <ul>
 *     <li>aktualizacji najlepszego rozwiazania na zakonczenie iteracji</li>
 *     <li>zakonczenia algorytmu genetycznego (szereg czasowy z predykcja)</li>
 *     <li>zakonczenia algorytmu genetycznego (rozwiazanie)</li>
 * </ul>
 */
public interface GAObserver {

    /**
     * Algorytm co iterację "pushuje" te dane do observerow.
     *
     * @param fitness Wartosc funkcji fitness najlepszego osobnika w tej iteracji
     * @param best Geny najlepszego osobnika w tej iteracji
     * @param i Numer iteracji
     */
    public void update(double fitness, double[] best, int i);

    /**
     * Metoda wywoływana po zakonczeniu dzialania algorytmu genetycznego, przekazuje szereg czasowy zawierajacy
     * predykcje lub nie, w zaleznosci od inicjalizacji/wywolania metody.
     *
     * @param timeSeries Szereg czasowy
     */
    public void done(TimeSeries timeSeries);

    /**
     * Metoda wywoływana po zakonczeniu dzialania algorytmu genetycznego, przekazuje rozwiazanie problemu
     * jako tablica doubli, w postaci zaleznej od metody predykcji.
     *
     * @param best Chromosom najlepszego osobnika jako tablica doubli
     */
    public void done(double[] best);
}
