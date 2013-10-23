package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;

public class GaussianPerturbation implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        double roll;
        Random rand = new Random();
        Chromosome[] newPopulation = new Chromosome[population.length];

        for(int i = 0; i < population.length; i++){

            Chromosome chromosome = new Chromosome(population[i].getGenes());
            newPopulation[i] = chromosome;

            for(int j = 0; j < chromosome.getSize(); j++){
                roll = rand.nextDouble();
                if(roll <= probability){
                    chromosome.addToGene(j, rand.nextGaussian());
                }
            }

        }
        return newPopulation;
    }
}
