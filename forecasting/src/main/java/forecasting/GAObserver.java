package forecasting;

import org.jfree.data.time.TimeSeries;

public interface GAObserver {

    /**
     * Algorytm co iterację "pushuje" te dane do observerow.
     *
     * @param fitness Wartosc funkcji fitness najlepszego osobnika w tej iteracji
     * @param best Geny najlepszego osobnika w tej iteracji
     * @param i Numer iteracji
     */
    public void update(double fitness, double[] best, int i);

    public void done(TimeSeries timeSeries);

    public void done(double[] best);
}
