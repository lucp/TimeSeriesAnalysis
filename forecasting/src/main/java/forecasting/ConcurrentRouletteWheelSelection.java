package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentRouletteWheelSelection implements AbstractGeneticAlgorithmOperation {

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
     * Wykonuje selekcje metoda ruletki
     *
     * @param population Populacja
     * @return Nowa populacja
     */
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        double fitnessSum = 0;
        for (Chromosome aPopulation : population) {
            fitnessSum += aPopulation.getFitness();
        }

        double[] probabilityTab = new double[population.length];

        for (int i = 0; i < probabilityTab.length; i++) {
            probabilityTab[i] = population[i].getFitness() / fitnessSum;
        }

        Chromosome[] tempPopulation = new Chromosome[population.length];

        Random rand = new Random();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < population.length; i++) {

            Runnable worker = new SelectionOperation(i,
                    rand,
                    population,
                    tempPopulation,
                    probabilityTab);

            executor.execute(worker);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return tempPopulation;
    }

    private class SelectionOperation extends Thread{

        private int i;
        private Random rand;
        private Chromosome[] population;
        private Chromosome[] tempPopulation;
        private double[] probabilityTab;

        private SelectionOperation(int i,
                                   Random rand,
                                   Chromosome[] population,
                                   Chromosome[] tempPopulation,
                                   double[] probabilityTab){
            this.i = i;
            this.rand = rand;
            this.population = population;
            this.tempPopulation = tempPopulation;
            this.probabilityTab = probabilityTab;
        }

        @Override
        public void run() {

            double roll = rand.nextDouble();
            double currentProb = 0;
            for (int j = 0; j < probabilityTab.length; j++) {
                currentProb += probabilityTab[j];
                if (roll <= currentProb) {
                    tempPopulation[i] = population[j];
                    break;
                }
            }
        }
    }

}
