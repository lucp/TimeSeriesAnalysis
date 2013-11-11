package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;

/**
 * Klasa implementuje algorytm selekcji metoda stochastic universal sampling
 */
public class StochasticUniversalSamplingSelection implements AbstractGeneticAlgorithmOperation {

    /**
     * Metoda nie posiada implementacji, gdyz selekcja jest szczegolnym przypadkiem operacji algorytmu genetycznego
     * dla ktorego nie definiuje sie prawdopodobienstwa
     *
     * @param probability Prawdopodobienstwo
     */
    @Override
    public void setProbability(double probability) {
    }

    /**
     * Wykonuje selekcje metoda stochastic universal sampling
     *
     * @param population Populacja
     * @return Nowa populacja
     */
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        double fitnessSum = 0;
        for (Chromosome chromosome : population) {
            fitnessSum += chromosome.getFitness();
        }

        Random rand = new Random();

        double p = fitnessSum / population.length;
        double start = p * rand.nextDouble();

        double fitnessPoints[] = new double[population.length];

        for (int i = 0; i < fitnessPoints.length; i++) {
            fitnessPoints[i] = start + i * p;
        }

        Chromosome[] tempPopulation = new Chromosome[population.length];
        int ix = 0;

        for (double point : fitnessPoints) {
            double lFitnessSum = 0;
            for(Chromosome chromosome : population){
                lFitnessSum += chromosome.getFitness();
                if(lFitnessSum >= point){
                    tempPopulation[ix++] = chromosome;
                    break;
                }
            }
        }

        return tempPopulation;
    }
}
