package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentGaussianPerturbation implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    /**
     * Ustawienie prawdopodobienstwa z jakim wykonuje sie mutacja.
     *
     * @param probability Prawdopodobienstwo
     */
    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * Wykonuje mutacje jako perturbacje Gaussa
     *
     * @param population Populacja
     * @return Nowa populacja
     */
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        Random rand = new Random();
        Chromosome[] newPopulation = new Chromosome[population.length];

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < population.length; i++) {

            Runnable worker = new MutationOperation(i,
                    rand,
                    population,
                    newPopulation);

            executor.execute(worker);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return newPopulation;
    }

    private class MutationOperation extends Thread{

        private int i;
        private Random rand;
        private Chromosome[] population;
        private Chromosome[] newPopulation;

        private MutationOperation(int i,
                                   Random rand,
                                   Chromosome[] population,
                                   Chromosome[] newPopulation){
            this.i = i;
            this.rand = rand;
            this.population = population;
            this.newPopulation = newPopulation;
        }

        @Override
        public void run() {

            Chromosome chromosome = new Chromosome(population[i].getGenes());
            newPopulation[i] = chromosome;

            double roll;

            for(int j = 0; j < chromosome.getSize(); j++){
                roll = rand.nextDouble();
                if(roll <= probability){
                    chromosome.addToGene(j, rand.nextGaussian());
                }
            }
        }
    }
}

