package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;

/**
 * Klasa implemetuje krzyzowanie arytmetyczne
 */
public class ArithmeticalCrossover implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    /**
     * Ustawienie prawdopodobienstwa z jakim wykonuje sie krzyzowanie.
     *
     * @param probability Prawdopodobienstwo
     */
    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * Wykonuje krzyzowanie
     *
     * @param population Populacja
     * @return Nowa populacja
     */
    @Override
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        int crossTabSize = population.length / 2;
        Chromosome[] crossTab1 = new Chromosome[crossTabSize];
        Chromosome[] crossTab2 = new Chromosome[crossTabSize];
        int[] matchTab = new int[population.length];

        for (int i = 0; i < population.length; i++) {
            matchTab[i] = -1;
        }

        int matchNo = 0;
        Random rand = new Random();

        for (int i = 0; i < crossTabSize; i++) {
            int match = rand.nextInt(population.length);
            while (matchTab[match] != -1) {
                match = rand.nextInt(population.length);
            }
            matchTab[match] = matchNo;
            crossTab1[matchNo] = population[match];
            match = rand.nextInt(population.length);
            while (matchTab[match] != -1) {
                match = rand.nextInt(population.length);
            }
            matchTab[match] = matchNo;
            crossTab2[matchNo] = population[match];
            matchNo++;
        }

        double lambda;
        int newPopulationIndex = 0;
        Chromosome[] newPopulation = new Chromosome[population.length];

        for (int i = 0; i < crossTabSize; i++) {

            Chromosome parent1 = crossTab1[i];
            Chromosome parent2 = crossTab2[i];

            double roll = rand.nextDouble();

            if(roll <= probability){

                lambda = rand.nextDouble();

                double[] offspring1Genes = new double[parent1.getSize()];
                double[] offspring2Genes = new double[parent2.getSize()];

                for (int j = 0; j < offspring1Genes.length; j++) {
                    offspring1Genes[j] = lambda * parent1.getGene(j) + (1 - lambda) * parent2.getGene(j);
                    offspring2Genes[j] = lambda * parent2.getGene(j) + (1 - lambda) * parent1.getGene(j);
                }

                Chromosome offspring1 = new Chromosome(offspring1Genes);
                Chromosome offspring2 = new Chromosome(offspring2Genes);

                newPopulation[newPopulationIndex++] = offspring1;
                newPopulation[newPopulationIndex++] = offspring2;

            }else{
                newPopulation[newPopulationIndex++] = parent1;
                newPopulation[newPopulationIndex++] = parent2;
            }
        }

        return newPopulation;
    }
}