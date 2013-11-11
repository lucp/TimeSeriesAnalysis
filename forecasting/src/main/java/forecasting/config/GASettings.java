package forecasting.config;

/**
 * Singleton zawierajacy ustawienia algorytmu genetycznego, pozwala na wybranie metody selekcji i
 * sposobu obliczania predykcji.
 */
public class GASettings {

    private static GASettings instance = new GASettings();

    private SelectionMethod selectionMethod = SelectionMethod.ROULETTE_WHEEL_SELECTION;
    private ForecastMethod forecastMethod = ForecastMethod.LINEAR_COMBINATION_FORECAST;

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
}
