package forecasting;

import forecasting.model.Chromosome;

public interface AbstractGeneticAlgorithmOperation {

    void setProbability(double probability);

    Chromosome[] performGeneticOperation(Chromosome[] population);
}
