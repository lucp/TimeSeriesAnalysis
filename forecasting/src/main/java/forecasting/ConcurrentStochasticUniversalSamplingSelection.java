package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentStochasticUniversalSamplingSelection implements AbstractGeneticAlgorithmOperation {

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

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < population.length; i++) {

            Runnable worker = new SelectionOperation(i,
                    population,
                    tempPopulation,
                    fitnessPoints);

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
        private Chromosome[] population;
        private Chromosome[] tempPopulation;
        private double[] points;

        private SelectionOperation(int i,
                                   Chromosome[] population,
                                   Chromosome[] tempPopulation,
                                   double[] points){
            this.i = i;
            this.population = population;
            this.tempPopulation = tempPopulation;
            this.points = points;
        }

        @Override
        public void run() {

            double lFitnessSum = 0;
            for(Chromosome chromosome : population){
                lFitnessSum += chromosome.getFitness();
                if(lFitnessSum >= points[i]){
                    tempPopulation[i] = chromosome;
                    break;
                }
            }
        }
    }
}
