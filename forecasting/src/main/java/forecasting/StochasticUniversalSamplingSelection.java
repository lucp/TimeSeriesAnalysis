package forecasting;

import forecasting.model.Chromosome;

import java.util.Random;

public class StochasticUniversalSamplingSelection implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        //TODO

        return null;
    }
}
