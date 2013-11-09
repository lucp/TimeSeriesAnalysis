package forecasting;

import forecasting.model.Chromosome;

/**
 * Interfejs operacji algorytmu genetycznego
 */
public interface AbstractGeneticAlgorithmOperation {

    /**
     * Ustawienie prawdopodobienstwa z jakim wykonuje sie operacja.
     *
     * @param probability Prawdopodobienstwo
     */
    void setProbability(double probability);

    /**
     * Wykonanie operacji
     *
     * @param population Populacja
     * @return Nowa populacja
     */
    Chromosome[] performGeneticOperation(Chromosome[] population);
}
