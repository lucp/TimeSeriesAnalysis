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
