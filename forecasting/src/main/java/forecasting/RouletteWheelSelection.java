package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;

public class RouletteWheelSelection implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        double fitnessSum = 0;
        for (Chromosome aPopulation : population) {
            fitnessSum += aPopulation.getFitness();
        }

        double probabilityTab[] = new double[population.length];

        for (int i = 0; i < probabilityTab.length; i++) {
            probabilityTab[i] = population[i].getFitness() / fitnessSum;
        }

        Chromosome[] tempPopulation = new Chromosome[population.length];

        Random rand = new Random();

        for (int i = 0; i < population.length; i++) {
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

        return tempPopulation;
    }
}