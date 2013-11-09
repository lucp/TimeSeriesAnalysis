package forecasting.model;

/**
 * Klasa modelowa okna czasowego
 */
public class SlidingTimeWindow {

    private int[] window;

    /**
     * Konstruktor
     *
     * @param window Okno czasowe jako tablica doubli
     */
    public SlidingTimeWindow(int[] window){
        this.window = window;
    }

    /**
     * Pobierz wartosc na danym indeksie
     *
     * @param index Indeks
     * @return Wartosc na danym indeksie
     */
    public int getValueAt(int index){
        return window[index];
    }

    /**
     * Pobierz rozmiar okna
     *
     * @return Rozmiar okna
     */
    public int getLength(){
        return window.length;
    }
}
