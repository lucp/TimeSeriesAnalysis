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
